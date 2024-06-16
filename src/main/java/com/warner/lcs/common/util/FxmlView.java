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
    },
    CLIENT_UPDATE {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("client.update.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/client_update.fxml";
        }
    },
    CLIENT_DELETE {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("client.delete.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/client_delete.fxml";
        }
    },
    ADDRESS_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("address.view.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/address_view.fxml";
        }
    },
    ADDRESS_REGISTER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("address.register.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/address_register.fxml";
        }
    },
    ADDRESS_UPDATE {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("address.update.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/address_update.fxml";
        }
    },
    ADDRESS_DELETE {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("address.delete.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/address_delete.fxml";
        }
    },

    INVOICE_REGISTER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("invoice.register.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/invoice_register.fxml";
        }
    },
    INVOICE_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("invoice.view.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/invoice_view.fxml";
        }
    },
    INVOICE_UPDATE {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("invoice.update.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/invoice_update.fxml";
        }
    },
    INVOICE_DELETE {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("invoice.delete.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/invoice_delete.fxml";
        }
    },
    INVOICE_MAIN_MENU {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("invoice.menu.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/invoices_menu.fxml";
        }
    },

    TREATMENT_MENU {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("treatment.menu.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/treatment_menu.fxml";
        }
    },

    TREATMENT_REGISTER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("treatment.register.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/treatment_register.fxml";
        }
    },

    TREATMENT_UPDATE {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("treatment.update.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/treatment_update.fxml";
        }
    },

    TREATMENT_DELETE {

        @Override
        public String getTitle() {
            return getStringFromResourceBundle("treatment.delete.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/treatment_delete.fxml";
        }
    },

    TREATMENT_VIEW {

        @Override
        public String getTitle() {
            return getStringFromResourceBundle("treatment.view.title");
        }

        @Override
        public String getFxmlFilePath() {
            return "src/main/resources/fxml/treatment_view.fxml";
        }
    };

    public abstract String getTitle();
    public abstract String getFxmlFilePath();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}