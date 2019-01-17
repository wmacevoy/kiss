/*
 * Copyright 2019 wmacevoy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kiss.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author wmacevoy
 */

// of(1,2,3,4)
// -> (1,2,3,4)

// of("a",range(0,2),1.3)
// -> ("a",0,1.3), ("a", 1, 1.3), ("a",2,1.3)

// of(choose("a","b"),33)
// -> ("a",33), ("b",33)

// of(choose("a","b"),range(0,2))
// -> ("a",0), ("a",1), ("a",2), ("b",0), ("b",1), ("b",2)

public interface Args  { // @args(1,seq(n),vals("a","b","c"))
    void get(int index, Object[] target, int offset);
    int size();
    int parameters();
    static Args values(Object... objs) {
        Args[] crossed = new Args[objs.length];
        int nn = 1;
        int i=0;
        while (i<objs.length) {
            if (objs[i] != null && objs[i] instanceof Args) {
              crossed[i]=(Args) objs[i];
              nn *= crossed[i].size();
              ++i;
            } else {
              int j=i+1;
              while (objs[j] != null && objs[j] instanceof Args) ++j;
              crossed[i]=new SequenceArgs(new Object[][] { 
                  Arrays.copyOfRange(objs,i,j)
              },j-i+1);
              i=j;
            }
        }
        if (nn == 0) {
            return NULL;
        } else {
            return new CrossArgs(crossed);
        }
    }
    
    static Args all(Object... args) {
        ArrayList<Object[]> seqArgs =new ArrayList<Object[]>();
        int parameters = 0;
        for (int i=0; i<args.length; ++i) {
            if (args[i] == null) {
                seqArgs.add(new Object[] {null});
            } else if (args[i] instanceof Object[]) {
                seqArgs.add((Object[]) args[i]);
            } else if (args[i] instanceof Args) {
                Args argi = (Args) args[i];
                seqArgs.ensureCapacity(seqArgs.size()+argi.size());
                for (int j=0; j<argi.size(); ++j) {
                    Object [] argj = new Object[argi.parameters()];
                    argi.get(j, argj, 0);
                    seqArgs.add(argj);
                }
            } else {
                seqArgs.add(new Object[][] { new Object[] { args[i] }});
            }
        }
        return new SequenceArgs((Object[][])seqArgs.toArray(),parameters);
    }
    static Args range(int end) {
        return range(0,end,1);
    }
    
    static Args range(int start, int end) {
        return range(start,end,1);
    }
    
    static Args range(int start, int end, int by) {
        int n = 0;
        for (int i=start; ((by > 0 && i<end) || (i > end)); i += by) {
            ++n;
        }
        Object[][] args = new Object[n][1];
        int k = 0;
        for (int i=start; ((by > 0 && i<=end) || (i > end)); i += by) {
            args[k][0]=Integer.valueOf(i);
            ++k;
        }
        return new SequenceArgs(args,1);
    }
    
    static Args NULL = new SequenceArgs(new Object[][] { }, 0);
}

class SequenceArgs implements Args {
    Object[][] args;
    int parameters;
    @Override
    public int size() { return args.length; }
    @Override
    public void get(int i, Object[] target, int offset) { 
        System.arraycopy(args[i],0,target,offset,parameters);
    }
    SequenceArgs(Object[][] args, int parameters) {
        this.args = args;
        this.parameters = parameters;
    }

    @Override
    public int parameters() {
        return this.parameters;
    }
}

class CrossArgs implements Args {
    Args[] args;
    int [] nn;
    int [] pn;
    int parameters;
    CrossArgs(Args[] args) {
        this.args = args;
        nn = new int[args.length];
        int n = 1;
        parameters = 0;
        for (int i=0; i<args.length; ++i) {
            pn[i]=parameters;
            parameters += args[i].parameters();
        }
        for (int i=args.length-1; i>=0; --i) {
            n *= args[i].size();
            nn[i]=n;
        }
    }
    @Override
    public int size() { return nn[0]; }
    
    @Override
    public int parameters() {
        return parameters;
    }
    
    @Override
    public void get(int k, Object [] target, int offset) {
        Object[] ans = new Object[parameters];
        for (int i=args.length-1; i>=0; --i) {
            int j = k % nn[i];
            k = k / nn[i];
            args[i].get(j,ans,pn[j]);
        }
    }
}
