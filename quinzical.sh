#!/bin/bash


# Install NZ voice for festival.
if [ -d "akl_nz_jdt_diphone" ] ; then
	sudo rm -r akl_nz_jdt_diphone
fi
sudo cp akl_nz_jdt_diphone.zip /usr/share/festival/voices/english
if ! [ -d "/usr/share/festival/voices/english/akl_nz_jdt_diphone" ] ; then
	sudo unzip /usr/share/festival/voices/english/akl_nz_jdt_diphone.zip -d /usr/share/festival/voices/english
fi

# Run the executable jar file.
java --module-path /home/se2062020/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml -jar quinzical.jar
