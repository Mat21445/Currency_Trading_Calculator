module com.example.cinkciarz {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;
    requires java.net.http;
//    requires gson;
//    requires com.google.gson;
//    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;


    opens com.example.cinkciarz to javafx.fxml;
    exports com.example.cinkciarz;
}