/*
* Copyright 2018 Infosys Ltd.
* Version: 1.0.0
*Use of this source code is governed by MIT license that can be found in the LICENSE file or at https://opensource.org/licenses/MIT.
*/

/*
* Date: 06-May-2019
* @version 1.0.0
* Description: 
*/

package com.infy.openjdk.ui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class JavaOpenFile {

    public static void main(String[] args) throws IOException {       
        File file = new File("D:\\openjdk\\testfile.txt");
        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) desktop.open(file);
       
    }

}