package com.android.onlineshop_castanheirofreno.database;

import android.os.AsyncTask;
import android.util.Log;

import com.android.onlineshop_castanheirofreno.database.entity.CategoryEntity;
import com.android.onlineshop_castanheirofreno.database.entity.CustomerEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ImageEntity;
import com.android.onlineshop_castanheirofreno.database.entity.ItemEntity;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;

import java.text.DateFormat;


public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addCustomer(final AppDatabase db, final String email, final String firstName,
                                    final String lastName, final String city, final int city_code, final String telephone, final String password) {
        CustomerEntity client = new CustomerEntity(email, firstName, lastName, city, city_code, telephone, password);
        long id = db.customerDao().insert(client);
    }

    private static void addOrder(final AppDatabase db, final double price, final String creation_date, final String delivery_date, final long idItem, final String status, final String owner) {
        OrderEntity order = new OrderEntity(price, creation_date, delivery_date,  idItem, status, owner );
        long id = db.orderDao().insert(order);
    }

    private static void addItem(final AppDatabase db, final String name, final String description, final double price, final long idCategory){//, final long idImage) {
        ItemEntity item = new ItemEntity(name, description, price, idCategory);//, idImage );
        long id = db.itemDao().insert(item);

    }

    private static void addCategory(final AppDatabase db, final String name, final String tag) {
        CategoryEntity category = new CategoryEntity(name, tag);
        long id = db.categoryDao().insert(category);
    }



    private static void populateWithTestData(AppDatabase db) {
        db.customerDao().deleteAll();

        addCustomer(db, "g@g.com", "Gabrielle", "Freno", "Martigny", 1920, "097900595", "12345");

        addCustomer(db, "t@t.com", "Tiago", "Castanheiro", "Martigny", 1920, "097900595", "56789");

        try {
            // Let's ensure that customers are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Categories
        db.categoryDao().deleteAll();
        addCategory(db, "Audio", "ic_audio");

        addCategory(db, "Camera", "ic_camera");

        addCategory(db, "Computer", "ic_computer");

        addCategory(db, "Gaming", "ic_gaming");

        addCategory(db, "Smartphone", "ic_smartphone");

        addCategory(db, "TV", "ic_tv");

        try {
            // Let's ensure that categories are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //Items
        db.itemDao().deleteAll();
        //Category 1 Audio
        addItem(db, "Apple EarPods", "Fonctions: Fonction mains libresCommande intégrée\n" +
                "Forme de port (type d'écouteurs): In-Ear\n" +
                "Bluetooth: Non\n" +
                "Connexions: 3.5 mm AUX\n" +
                "Connexions audio: Line in 3.5mm", 35.00, 1);

        addItem(db, "AKG Y100 Wireless", "Autonomie de fonctionnement (jusqu'à): 8 h\n" +
                "Fonctions: Fonction mains libres\n" +
                "Forme de port (type d'écouteurs): In-Ear\n" +
                "Bande de fréquences: 20 Hz - 20 kHz\n" +
                "Connectivité: Bluetooth", 69.30, 1);

        addItem(db, "JBL Tune 120TWS", "Autonomie de fonctionnement (jusqu'à): 16 h\n" +
                "Fonctions: Commande intégréeFonction mains libres\n" +
                "Forme de port (type d'écouteurs): In-Ear\n" +
                "Bluetooth: Oui\n" +
                "Assistant linguistique: SiriGoogle Assistant", 79.90, 1);

        addItem(db, "Beats By Dr. Dre", "Fonctions: Commande intégréeFonction mains libres\n" +
                "Forme de port (type d'écouteurs): In-Ear\n" +
                "Assistant linguistique: Siri\n" +
                "Connexions audio: Line in 3.5mm\n" +
                "Commande intégrée: Oui", 64.90, 1);

        addItem(db, "Bose QuietComfort ", "Autonomie de fonctionnement (jusqu'à): 20 h\n" +
                "Fonctions: Active Noise CancelingPliableFonction mains libresCommande intégrée\n" +
                "Forme de port (type d'écouteurs): Over-Ear\n" +
                "Bluetooth: Oui\n" +
                "Assistant linguistique: Google AssistantSiri", 399.00, 1);

        addItem(db, "Bowers & Wilkins PX", "Autonomie de fonctionnement (jusqu'à): 22 h\n" +
                "Fonctions: Pliable\n" +
                "Forme de port (type d'écouteurs): Over-Ear\n" +
                "Bluetooth: Oui\n" +
                "Suppresseur de bruit actif (Active Noise Canceling): Oui", 429.00, 1);

        addItem(db, "B&O Beoplay E8 ", "Fonctions: Commande intégréeFonction mains libres\n" +
                "Forme de port (type d'écouteurs): In-Ear\n" +
                "Commande intégrée: Oui\n" +
                "Bande de fréquences: 20–20.000 Hz\n" +
                "Connectivité: Bluetooth", 349.00, 1);

        addItem(db, "Sony WH-1000XM3B", "Autonomie de fonctionnement (jusqu'à): 30 h\n" +
                "Fonctions: Active Noise CancelingFonction mains libresCommande intégréePliable\n" +
                "Forme de port (type d'écouteurs): Over-Ear\n" +
                "Bluetooth: Oui\n" +
                "Assistant linguistique: Google AssistantSiri", 349.00, 1);

        addItem(db, "Pioneer DJ HDJ", "Fonctions: PliableRésistant à la transpiration\n" +
                "Forme de port (type d'écouteurs): Over-Ear\n" +
                "Bluetooth: Non\n" +
                "Suppresseur de bruit actif (Active Noise Canceling): Non\n" +
                "Connexions audio: Line in 6.3mm", 339.00, 1);

        addItem(db, "Panasonic RP-HD", "Autonomie de fonctionnement (jusqu'à): 20 h\n" +
                "Portée de transmission: 10 m\n" +
                "Fonctions: Commande intégréeFonction mains libresPliable\n" +
                "Forme de port (type d'écouteurs): Over-Ear\n" +
                "Bluetooth: Oui", 299.00, 1);

        addItem(db, "V-Moda Crossfade", "Autonomie de fonctionnement (jusqu'à): 14 h\n" +
                "Bluetooth: Oui\n" +
                "Bande de fréquences: 5Hz - 40kHz", 289.00, 1);

        //Category 2 Camera

        addItem(db, "Canon EOS 4000D", "Type de capteur optique: CMOS\n" +
                "Résolution de la prise de vue: 18 Mpx\n" +
                "Résolution de l’enregistrement vidéo: Full HD (1920x1080)\n" +
                "Distance focale finale: 55 mm\n" +
                "Processeur d'images: DIGIC 4+", 199.00, 2);

        addItem(db, "FUJIFILM Instax Mini", "Equipement: Flash de l?appareil photo\n" +
                "Distance focale: 60mm\n" +
                "Distance focale initiale: 60 mm\n" +
                "Distance focale finale: 60 mm\n" +
                "Fonctions supplémentaires: un miroir pour les selfies", 69.90, 2);

        addItem(db, "Sony Alpha 6400", "Résolution de la prise de vue: 24.2 Mpx\n" +
                "Type de capteur optique: APS-C, (23,5 x 15,6 mm) EXMOR® CMOS Sensor\n" +
                "Résolution de l'Ecran: 921600 Mpx\n" +
                "Images par seconde foto: 11\n" +
                "Images par seconde film: 30 Mpx", 1349.00, 2);

        addItem(db, "Canon IXUS 285", "Taille de l'Ecran: 3 \n" +
                "Résolution de l'Ecran: 461.000\n" +
                "Type de batterie: Lithium-ion (Li-Ion)\n" +
                "Fonctions prises de vue: Enregistrement vidéo\n" +
                "Connexions sans fil: WiFiNFC", 199.00, 2);

        addItem(db, "Sony DSC-W810", "Taille de l'Ecran: 2.7 \n" +
                "Résolution de l'Ecran: 230.400\n" +
                "Type de batterie: Lithium-ion (Li-Ion)\n" +
                "Fonctions prises de vue: Enregistrement vidéo\n" +
                "Cartes mémoire prises en charge: SDSDHCSDXC", 99.90, 2);

        addItem(db, "FUJIFILM Instax", "Fonctions supplémentaires: Taille d'image: 62mm x 46mm\n" +
                "Accessoires inclus: Dragonne \n bague de dragonne \n garantie \n mode d'emploi \n 2 piles lithium-ion CR2", 111.80, 2);

        addItem(db, "Canon Powershot ", "Type de capteur optique: CMOS\n" +
                "Résolution de la prise de vue: 18 Mpx\n" +
                "Résolution de l’enregistrement vidéo: Full HD (1920x1080)\n" +
                "Distance focale finale: 55 mm\n" +
                "Processeur d'images: DIGIC 4+", 233.20, 2);

        addItem(db, "Panasonic FZ2000", "Résolution de l’enregistrement vidéo: 4k (3840×2160)\n" +
                "Equipement: Viseur electronique\n" +
                "Connexions sans fil: WiFi\n" +
                "Cartes mémoire prises en charge: SDSDHCSDXC", 1100.30, 2);

        addItem(db, "Panasonic DMC-GX", "Résolution photo: 16 Mpx\n" +
                "Images par seconde foto: 10\n" +
                "Zoom optique: 2.6 x\n" +
                "Equipement: Flash de l?appareil photo", 699.00, 2);

        addItem(db, "Canon Zoemini S", "Résolution de la prise de vue: 8 Mpx\n" +
                "Type de batterie: Lithium Polymer (LiPo)\n" +
                "Equipement: Flash de l?appareil photo\n" +
                "Connexions sans fil: Bluetooth\n" +
                "Cartes mémoire prises en charge: SD micro", 179.00, 2);


        //Category 3 Computer
        addItem(db, "Acer Nitro 5", "Diagonale de l'Ecran: 15.6 \n" +
                "Mémoire vive: 16 GB\n" +
                "Capacité HDD: 1 TB\n" +
                "Capacité SSD: 512 TB\n" +
                "Carte graphique: Nvidia GeForce GTX 1650", 1699.00, 3);

        addItem(db, "Acer Swift 3" , "ype d'Ecran: 14" +
                "\n Full HD IPS (1920 x 1080) matt, narrow bezel\n" +
                "Diagonale de l'Ecran: 14 \n" +
                "Processeur: Intel Core i5-1035G1 (1 GHz)\n" +
                "Mémoire vive: 8 GB\n" +
                "Capacité SSD: 1 TB", 799.00, 3);

        addItem(db, "Apple CTO MacBook", "Diagonale de l'Ecran: 13 \n" +
                "Mémoire vive: 16 GB\n" +
                "Capacité SSD: 512 GB\n" +
                "Carte graphique: Intel Iris Plus Graphics 655\n" +
                "Dispositif de pointage: TouchpadWebcamTouchbar", 2469.00, 3);

        addItem(db, "Apple CTO MacBook Pro 16", "Type d'Ecran: 16\n" +
                " Retina IPS (3072 x 1920)\n" +
                "Diagonale de l'Ecran: 16 \"\n" +
                "Processeur: Intel Core i9 8-Core (2.4 GHz)\n" +
                "Mémoire vive: 16 GB\n" +
                "Capacité SSD: 4 TB", 4404.00, 3);

        addItem(db, "Asus W202NA", "Diagonale de l'Ecran: 11.6 \n" +
                "Processeur: Intel Celeron N3350\n" +
                "Mémoire vive: 4 GB\n" +
                "Mémoire interne: 64 GB\n" +
                "Chip graphique: Intel HD Graphics 500", 349.00, 3);

        addItem(db, "Asus ZenBook 14", "Diagonale de l'Ecran: 14 \n" +
                "Processeur: Intel Core i5-10210U\n" +
                "Mémoire vive: 8 GB\n" +
                "Capacité SSD: 512 GB\n" +
                "Carte graphique: Nvidia GeForce MX250 ", 1259.00, 3);

        addItem(db, "Dell Precision 3540", "Diagonale de l'Ecran: 15.6 \n" +
                "Processeur: Intel Core i7-8665U\n" +
                "Mémoire vive: 32 GB\n" +
                "Capacité SSD: 1000 GB\n" +
                "Carte graphique: AMD Radeon Pro WX 2100 ", 2199.00, 3);

        addItem(db, "HP 15-dw0456nz ", "Type d'Ecran: 15.6 " +
                "\n FHD-SVA-Display\n" +
                "Diagonale de l'Ecran: 15.6 \n" +
                "Processeur: Intel Pentium® Gold 4417U\n" +
                "Mémoire vive: 8 GB\n" +
                "Capacité SSD: 256 GB", 499.00, 3);

        addItem(db, "Lenovo ThinkPad T490s", "Type d'Ecran: IPS\n" +
                "Diagonale de l'Ecran: 14 \n" +
                "Processeur: Intel Core i7-8565U\n" +
                "Mémoire vive: 16 GB\n" +
                "Capacité SSD: 512 GB", 2249.00, 3);

        addItem(db, "Microsoft Surface Laptop 2", "Type d'Ecran: 13.5" +
                "\n PixelSense Touch (2256 x 1504)\n" +
                "Diagonale de l'Ecran: 13.5 \"\n" +
                "Processeur: Intel Core i5-8250U\n" +
                "Mémoire vive: 8 GB\n" +
                "Capacité SSD: 256 GB", 1499.00, 3);

        addItem(db, "Vaio SX14 i7", "Type d'Ecran: IPS\n" +
                "Diagonale de l'Ecran: 14 \"\n" +
                "Processeur: Intel Core i7-8565U\n" +
                "Mémoire vive: 16 GB\n" +
                "Capacité SSD: 512 GB", 1999.00, 3);


        //Category 4 Gaming

        addItem(db, "Sony PlayStation 4 Pro", "bb" , 45, 4);

        addItem(db, "aa", "Plate-forme: Sony PlayStation 4\n" +
                "Lecteur: BD × 6 CAV DVD × 8 CA\n" +
                "Processeur: Single-chip custom processor CPU: x86-64 AMD “Jaguar”, 8 cores GPU: 4.20 TFLOPS, AMD Radeon™ based graphics engine\n" +
                "Capacité mémoire du disque dur: 1 TB\n" +
                "Mémoire flash intégrée: 8 GB" , 319.00, 4);

        addItem(db, "Nintendo Switch Neon", "bb" , 45, 4);

        addItem(db, "aa", "Plate-forme: Nintendo Switch\n" +
                "Processeur: Technologie de processeur Nvidia Tegra\n" +
                "Mémoire flash intégrée: 32 GB\n" +
                "Caractéristiques: Mode téléviseur, mode table, mode portable\n" +
                "Type d'Ecran: Écran tactile capacitif / LCD 6,2 pouces / résolution : 1280 x 720 pixels" , 319.00, 4);

        addItem(db, "Sony PlayStation Classic", "Console originale" , 69.90, 4);

        addItem(db, "Nintendo Switch Lite", "Plate-forme: Nintendo Switch\n" +
                "Capacité mémoire du disque dur: 32 GB", 219.00, 4);

        addItem(db, "PS4 - Red Dead Redemption 2 ", "Consoles de jeux vidéo compatibles: PlayStation 4" , 39.90, 4);

        addItem(db, "Xbox One - Borderlands 3", "Consoles de jeux vidéo compatibles: Xbox One" , 39.90, 4);

        addItem(db, "PC - The Sims 4", "Consoles de jeux vidéo compatibles: PC" , 29.90, 4);

        addItem(db, "NSW - Just Dance 2020", "Consoles de jeux vidéo compatibles: Switch" , 39.90, 4);

        //Category 5 Smartphone

        addItem(db, "Samsung Galaxy S20", "Mémoire interne: 128 GB\n" +
                "Extension de mémoire: 1 TB\n" +
                "Mémoire vive: 12 GB\n" +
                "Résolution caméra arrière: 108 Mpx\n" +
                "Résolution caméra avant: 40 Mpx" , 1349.00, 5);

        addItem(db, "Apple iPhone 8", "Mémoire interne: 64 GB\n" +
                "Résolution caméra arrière: 12 Mpx\n" +
                "Résolution caméra avant: 7 Mpx\n" +
                "Taille de l'Ecran: 4.7 \"\n" +
                "Type d'Ecran: Retina" , 529.00, 5);

        addItem(db, "Apple iPhone 11 Pro", "Mémoire interne: 64 GB\n" +
                "Mémoire vive: 6 GB\n" +
                "Résolution caméra arrière: 12 Mpx\n" +
                "Résolution caméra avant: 12 Mpx\n" +
                "Fonction caméra: Optical ZoomMode nocturneFlash intégré4K Video RecordingAutofocusDigital ZoomCaméra avantFlash LEDTéléobjectifUltra grand-AngleEnregistrements vidéomontage vidéoGrand-Angle" , 1249.00, 5);

        addItem(db, "Apple iPhone XR", "Mémoire interne: 64 GB\n" +
                "Mémoire vive: 3 GB\n" +
                "Résolution caméra arrière: 12 Mpx\n" +
                "Résolution caméra avant: 7 Mpx\n" +
                "Taille de l'Ecran: 6.1 \"" , 629.00, 5);

        addItem(db, "Huawei P smart ", "Mémoire interne: 64 GB\n" +
                "Extension de mémoire: 512 GB\n" +
                "Mémoire vive: 3 GB\n" +
                "Résolution caméra arrière: 13 Mpx\n" +
                "Résolution caméra avant: 8 Mpx" , 249.00, 5);

        addItem(db, "Huawei P30 lite", "Mémoire interne: 128 GB\n" +
                "Extension de mémoire: 512 GB\n" +
                "Mémoire vive: 4 GB\n" +
                "Résolution caméra arrière: 48 Mpx\n" +
                "Résolution caméra avant: 24 Mpx" , 299.00, 5);

        addItem(db, "Samsung Galaxy A40", "Mémoire interne: 64 GB\n" +
                "Extension de mémoire: 512 GB\n" +
                "Mémoire vive: 4 GB\n" +
                "Résolution caméra arrière: 16 Mpx\n" +
                "Résolution caméra avant: 25 Mpx" , 245.00, 5);

        addItem(db, "Samsung Note10", "Mémoire interne: 128 GB\n" +
                "Extension de mémoire: 1 TB\n" +
                "Mémoire vive: 6 GB\n" +
                "Résolution caméra arrière: 12 Mpx\n" +
                "Résolution caméra avant: 32 Mpx" , 599.00, 5);

        addItem(db, "Samsung Galaxy A51", "Mémoire interne: 128 GB\n" +
                "Extension de mémoire: 512 GB\n" +
                "Mémoire vive: 4 GB\n" +
                "Résolution caméra arrière: 48 Mpx\n" +
                "Résolution caméra avant: 32 Mpx" , 369.00, 5);

        addItem(db, "Apple iPhone 11", "Mémoire interne: 256 GB\n" +
                "Mémoire vive: 6 GB\n" +
                "Résolution caméra arrière: 12 Mpx\n" +
                "Résolution caméra avant: 12 Mpx\n" +
                "Fonction caméra: Optical ZoomMode nocturneFlash intégré4K Video RecordingAutofocusDigital ZoomCaméra avantFlash LEDTéléobjectifUltra grand-AngleEnregistrements vidéomontage vidéoGrand-Angle" , 1369.00, 5);



        //Category 6 TV

        addItem(db, "Samsung QE", "Diagonale de l'Ecran en cm: 138 cm\n" +
                "Diagonale de l'Ecran en pouce: 55 \"\n" +
                "Syntoniseur télévision numérique: 2 x DVB-T2/C/S2, 1 x CI+\n" +
                "Format d'affichage: 4K Ultra HD\n" +
                "Résolution: 3840 x 2160" , 649.00, 6);

        addItem(db, "Panasonic TX-50", "Diagonale de l'Ecran en cm: 126 cm\n" +
                "Diagonale de l'Ecran en pouce: 50 \"\n" +
                "Syntoniseur télévision numérique: DVB-T2/C/S2 CI+\n" +
                "Format d'affichage: 4K Ultra HD\n" +
                "Résolution: 3840 x 2160" , 549.00, 6);

        addItem(db, "LG 55UM", "Diagonale de l'Ecran en cm: 139 cm\n" +
                "Diagonale de l'Ecran en pouce: 55 \"\n" +
                "Syntoniseur télévision numérique: DVB-T2/C/S2 CI+\n" +
                "Format d'affichage: 4K Ultra HD\n" +
                "Résolution: 3840 x 2160" , 499.00, 6);

        addItem(db, "Samsung QE-65Q", "Diagonale de l'Ecran en cm: 163 cm\n" +
                "Diagonale de l'Ecran en pouce: 65 \"\n" +
                "Syntoniseur télévision numérique: 2 x DVB-T2/C/S2, 1 x CI+\n" +
                "Format d'affichage: 4K Ultra HD\n" +
                "Résolution: 3840 x 2160" , 749.00, 6);

        addItem(db, "Panasonic TX-65GZ", "Diagonale de l'Ecran en cm: 164 cm\n" +
                "Diagonale de l'Ecran en pouce: 65 \"\n" +
                "Syntoniseur télévision numérique: 2 x DVB-T2/C/S2 CI+\n" +
                "Format d'affichage: 4K Ultra HD\n" +
                "Résolution: 3840 x 2160" , 1499.00, 6);

        addItem(db, "Chiq U50Q5T", "Diagonale de l'Ecran en cm: 126 cm\n" +
                "Diagonale de l'Ecran en pouce: 50 \"\n" +
                "Syntoniseur télévision numérique: DVB-T2/C/S2 CI+\n" +
                "Format d'affichage: 4K Ultra HD\n" +
                "Résolution: 3840 x 2160" , 299.00, 6);

        addItem(db, "LG 75UM7600", "Diagonale de l'Ecran en cm: 189 cm\n" +
                "Diagonale de l'Ecran en pouce: 75 \"\n" +
                "Syntoniseur télévision numérique: DVB-T2/C/S2 CI+\n" +
                "Format d'affichage: 4K Ultra HD\n" +
                "Résolution: 3840 x 2160" , 999.00, 6);

        addItem(db, "Sony KD-55AG9", "Diagonale de l'Ecran en cm: 139 cm\n" +
                "Diagonale de l'Ecran en pouce: 55 \"\n" +
                "Syntoniseur télévision numérique: 2 x DVB-T2/C/S2, 1 x CI+\n" +
                "Format d'affichage: 4K Ultra HD\n" +
                "Résolution: 3840 x 2160" , 1499.00, 6);

        addItem(db, "Samsung QE-65LS03R", "Diagonale de l'Ecran en cm: 163 cm\n" +
                "Diagonale de l'Ecran en pouce: 65 \"\n" +
                "Syntoniseur télévision numérique: DVB-T2/C/S2 CI+\n" +
                "Format d'affichage: 4K Ultra HD\n" +
                "Résolution: 3840 x 2160" , 2799.00, 6);

        addItem(db, "Philips 65OLED854", "Diagonale de l'Ecran en cm: 164 cm\n" +
                "Diagonale de l'Ecran en pouce: 65 \"\n" +
                "Syntoniseur télévision numérique: 2 x DVB-T2/C/S2, 1 x CI+\n" +
                "Format d'affichage: 4K Ultra HD\n" +
                "Résolution: 3840 x 2160" , 4499.00, 6);

        addItem(db, "Panasonic TX-55GZC1004", "Diagonale de l'Ecran en cm: 139 cm\n" +
                "Diagonale de l'Ecran en pouce: 55 \"\n" +
                "Syntoniseur télévision numérique: 2 x DVB-T2/C/S2 CI+\n" +
                "Format d'affichage: 4K Ultra HD\n" +
                "Résolution: 3840 x 2160" , 2799.00, 6);

        try {
            // Let's ensure that the items are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Orders
        db.orderDao().deleteAll();
        addOrder(db, 429, "23.03.2020", "23.03.2020", 2, "Delivered", "g@g.com");
        addOrder(db, 429, "23.03.2020", "23.03.2020", 2, "Delivered", "g@g.com");
        addOrder(db, 429, "23.03.2020", "23.03.2020", 2, "Delivered", "t@t.com");
        addOrder(db, 429, "25.05.2020", "", 1, "In progress", "t@t.com");
        addOrder(db, 429, "26.03.2020", "", 1, "In progress", "t@t.com");

        try {
            // Let's ensure that orders are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}