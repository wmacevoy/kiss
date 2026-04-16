#!/usr/bin/env python3
"""HTTP server with Range header support (required by CheerpJ)."""
import os, sys
from http.server import HTTPServer, SimpleHTTPRequestHandler

class RangeHandler(SimpleHTTPRequestHandler):
    def do_GET(self):
        path = self.translate_path(self.path)
        if not os.path.isfile(path) or not self.headers.get("Range"):
            return super().do_GET()

        try:
            r = self.headers["Range"].replace("bytes=", "")
            parts = r.split("-")
            size = os.path.getsize(path)
            start = int(parts[0]) if parts[0] else 0
            end = int(parts[1]) if parts[1] else size - 1
            length = end - start + 1
        except Exception:
            self.send_error(416, "Bad range")
            return

        self.send_response(206)
        self.send_header("Content-Type", self.guess_type(path))
        self.send_header("Content-Range", "bytes %d-%d/%d" % (start, end, size))
        self.send_header("Content-Length", str(length))
        self.send_header("Accept-Ranges", "bytes")
        self.end_headers()

        with open(path, "rb") as f:
            f.seek(start)
            self.wfile.write(f.read(length))

port = int(sys.argv[1]) if len(sys.argv) > 1 else 8080
os.chdir(os.path.dirname(os.path.abspath(__file__)))
print("Serving on http://localhost:%d" % port, flush=True)
HTTPServer(("", port), RangeHandler).serve_forever()
