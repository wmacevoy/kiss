#!/usr/bin/env python3
"""HTTP server with Range header support (required by CheerpJ)."""
import os, sys
from http.server import HTTPServer, SimpleHTTPRequestHandler

class RangeHandler(SimpleHTTPRequestHandler):
    def send_head(self):
        path = self.translate_path(self.path)
        if not os.path.isfile(path):
            return super().send_head()

        r = self.headers.get("Range")
        if r is None:
            return super().send_head()

        # Parse Range: bytes=start-end
        try:
            r = r.replace("bytes=", "")
            start, end = r.split("-")
            size = os.path.getsize(path)
            start = int(start) if start else 0
            end = int(end) if end else size - 1
            length = end - start + 1
        except Exception:
            self.send_error(416, "Bad range")
            return None

        f = open(path, "rb")
        f.seek(start)
        self.send_response(206)
        self.send_header("Content-Type", self.guess_type(path))
        self.send_header("Content-Range", f"bytes {start}-{end}/{size}")
        self.send_header("Content-Length", str(length))
        self.send_header("Accept-Ranges", "bytes")
        self.end_headers()
        return f

port = int(sys.argv[1]) if len(sys.argv) > 1 else 8080
os.chdir(os.path.dirname(os.path.abspath(__file__)))
print(f"Serving on http://localhost:{port}")
HTTPServer(("", port), RangeHandler).serve_forever()
