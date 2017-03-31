package helper;
/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

import javax.swing.*;
import java.util.ResourceBundle;

public class PdfPrintHelper {
    public static void main(String[] args) {
        // Get a file from the command line to open
        final String filePath = args[0];

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // build a component controller
                SwingController controller = new SwingController();
                controller.setIsEmbeddedComponent(true);

                PropertiesManager properties = new PropertiesManager(
                        System.getProperties(),
                        ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));

                // read/store the font cache.
                ResourceBundle messageBundle = ResourceBundle.getBundle(
                        PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                new FontPropertiesManager(properties, System.getProperties(), messageBundle);

                properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "0.75");
                properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_OPEN, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_SAVE, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_SEARCH, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_PRINT, "true");
                properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_UPANE, "false");
                properties.set(
                    PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION_HIGHLIGHT,
                    "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION_TEXT,
                    "false");
                properties.set(
                    PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION_SELECTION,
                    "false");
                // hide the status bar
                properties.set(PropertiesManager.PROPERTY_SHOW_STATUSBAR, "false");
                // hide a few toolbars, just to show how the prefered size of the viewer
                // changes.
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FORMS, "false");

                SwingViewBuilder factory = new SwingViewBuilder(controller, properties);

                // add interactive mouse link annotation support via callback
                controller.getDocumentViewController().setAnnotationCallback(
                        new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
                JPanel viewerComponentPanel = factory.buildViewerPanel();
                JFrame applicationFrame = new JFrame();
                applicationFrame.setTitle("Drucken");
                //applicationFrame.setIconImage("file:resources/images/archiv_icon.png");
                applicationFrame.setSize(500, 700);
                applicationFrame.setResizable(false);
                applicationFrame.setLocationRelativeTo(null);
                applicationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                applicationFrame.getContentPane().add(viewerComponentPanel);
                // Now that the GUI is all in place, we can try openning a PDF
                controller.openDocument(filePath);

                // add the window event callback to dispose the controller and
                // currently open document.
                applicationFrame.addWindowListener(controller);

                // show the component
                applicationFrame.pack();
                applicationFrame.setVisible(true);
            }
        });


    }
}
