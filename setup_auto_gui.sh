#!/bin/bash

# --------------------------------------
# Step 0: Update system
# --------------------------------------
sudo apt update -y && sudo apt upgrade -y

# --------------------------------------
# Step 1: Remove old Java
# --------------------------------------
sudo apt remove openjdk-11-* -y
sudo apt autoremove -y

# --------------------------------------
# Step 2: Install dependencies
# --------------------------------------
sudo apt install wget tar xfce4 xfce4-goodies tigervnc-standalone-server tigervnc-common unzip -y

# --------------------------------------
# Step 3: Install OpenJDK 21
# --------------------------------------
wget https://download.java.net/java/GA/jdk21/0d1cfde4252546c6931946de8db48ee2/11/GPL/openjdk-21_linux-x64_bin.tar.gz
sudo mkdir -p /usr/lib/jvm
sudo tar -xzf openjdk-21_linux-x64_bin.tar.gz -C /usr/lib/jvm
sudo mv /usr/lib/jvm/jdk-21 /usr/lib/jvm/java-21-openjdk

# Update alternatives
sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/java-21-openjdk/bin/java 1
sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/java-21-openjdk/bin/javac 1
sudo update-alternatives --set java /usr/lib/jvm/java-21-openjdk/bin/java
sudo update-alternatives --set javac /usr/lib/jvm/java-21-openjdk/bin/javac

# Verify Java
java -version
javac -version

# --------------------------------------
# Step 4: Set up VNC
# --------------------------------------
mkdir -p ~/.vnc
echo "123456" | vncpasswd -f > ~/.vnc/passwd
chmod 600 ~/.vnc/passwd

# Create xstartup for XFCE
cat <<EOL > ~/.vnc/xstartup
#!/bin/bash
xrdb $HOME/.Xresources
startxfce4 &

# Wait a few seconds for desktop to start
sleep 5

# Navigate to your project
cd ~/Ngoni-Banking-App || exit

# Compile JavaFX app (replace Main.java with your main class if needed)
javac --module-path ~/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml -d out src/**/*.java

# Run JavaFX app
java --module-path ~/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml -cp out Main
EOL
chmod +x ~/.vnc/xstartup

# --------------------------------------
# Step 5: Download JavaFX SDK
# --------------------------------------
wget https://gluonhq.com/download/javafx-21/ -O javafx-sdk-21.zip
unzip javafx-sdk-21.zip -d ~/javafx-sdk-21

# --------------------------------------
# Step 6: Start VNC server
# --------------------------------------
vncserver :1 -geometry 1280x720 -depth 24
echo "VNC server started on display :1 (port 5901) with password '123456'."
echo "Your Banking App GUI should auto-launch inside VNC."
