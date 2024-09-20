module es.aketzagonzalez {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens es.aketzagonzalez.borrarElementosDeTabla to javafx.fxml;
    exports es.aketzagonzalez.borrarElementosDeTabla;
}