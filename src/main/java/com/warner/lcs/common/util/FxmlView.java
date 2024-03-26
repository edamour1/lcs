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
            return "fxml/FXMLDocument.fxml";
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
    },
    CLIENT_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("client.view.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/client_view.fxml";
        }
    },
    CLIENT_REGISTER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("client.register.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/client_register.fxml";
        }
    };

    public abstract String getTitle();
    public abstract String getFxmlFilePath();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
