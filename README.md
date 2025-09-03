# edt-univ

Application Android pour consulter l’emploi du temps universitaire, avec un script Python pour générer le JSON à partir d’un fichier `.ics`.

---

## 📂 Structure du projet
```text
Univ-Calendar/
├── UnivCalendar/ # Projet Android Studio
└── Python/ # Script Python pour convertir .ics en JSON
```

---

## 🚀 Fonctionnalités

### Android
- Affichage de l’emploi du temps dans un **RecyclerView**.  
- Navigation avec **DrawerLayout**.  
- Sélection de la date (aujourd’hui, précédent, suivant).  
- Couleurs et bordures dynamiques selon le type de cours cliqué pour afficher la description.  

### Python
- Lecture de fichiers `.ics`.  
- Conversion en JSON exploitable par l’application Android.  
- API minimale pour servir le JSON (ex: Flask ou FastAPI).

---

## 💻 Installation et utilisation

### Python
1. Installer les dépendances :
```bash
pip install -r requirements.txt
```
2. Lancer le serveur :
```bash
python script.py
```
3. Vérifier que le JSON est accessible :

(http://<IP_PC>:5000/edt)

Android

Ouvrir le projet AndroidApp/ avec Android Studio.

Vérifier l’URL de l’API dans le code (variable baseUrl).

Lancer sur un émulateur ou appareil réel ou branchez votre appareil pour transférer l'application
