package com.shahid.fashionista_mobile.callbacks;

import java.io.File;

public interface onFileChangeListener {
    void registerFile(File file);

    void unregisterFile(int position);
}
