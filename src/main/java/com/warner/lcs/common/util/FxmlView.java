package com.warner.lcs.common.util;

import java.util.ResourceBundle;

public enum FxmlView {
    LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "com/warner/lcs/FXMLDocument.fxml";
        }
    },
    MAIN_MENU {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("main.menu.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/main_menu.fxml";
        }
    },
    CLIENT_MENU {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("client.menu.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/clients_menu.fxml";
        }
    };

    public abstract String getTitle();
    public abstract String getFxmlFilePath();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
