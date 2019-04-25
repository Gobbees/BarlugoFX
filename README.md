# BarlugoFX

Universal photo editor made with <3 in Italy



In order to use BarlugoFX with bigger images, we higly suggest launching it with the command

-java -XmxNg barlugoFX

Where N is the number of Gigabytes you want to allocate for the program. We highly suggest to use at least 4Gb of ram for the program to function properly on big images (4K and over). 


Have fun using it!

## List of known bugs
* On some linux distribution rarely the image is loaded with some white band on left and right. Closing and reopening the application will resolve the issue. 
* Triggering a Ctrl+Z shortcut while a textfield is focused will throw a NullPointerException on the JavaFX Application Thread. This doesn't cause any problem though.