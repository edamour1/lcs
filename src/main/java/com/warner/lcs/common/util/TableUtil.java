package com.warner.lcs.common.util;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableUtil {

    public static <S, T> TableColumn<S, T> createTableColumn(String columnName, String propertyName) {
        TableColumn<S, T> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        return column;
    }

    public static <T> ObservableList<T> getObservableList(List<T> dataList) {
        ObservableList<T> observableList = FXCollections.observableArrayList();
        for (T data : dataList) {
            observableList.add(data);
        }
        return observableList;
    }

}
