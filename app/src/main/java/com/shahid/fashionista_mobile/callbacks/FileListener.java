package com.shahid.fashionista_mobile.callbacks;

import java.io.File;

public interface FileListener {
    void registerFile(File file);

    void unregisterFile(int position);
}
