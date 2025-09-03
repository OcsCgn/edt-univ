from ics import Calendar
from flask import Flask,jsonify  # Pour faire une petit api web
import os



app = Flask(__name__)

#Le fichier ICS à lire
ICS_FILE = "ical.ics"

# Fonction pour lire et parser le fichier ICS et retourner les événements sous forme de liste de dictionnaires
def parse_ics(file_path):
	with open(file_path, "r", encoding="utf-8") as f:
		c = Calendar(f.read())
	events = []
	for event in c.events:
		events.append({
			"name": event.name,
			"start": str(event.begin),
			"end": str(event.end),
			"description": event.description if event.description else "",
			"location": event.location if event.location else ""
		})
	return events




# Créer la page "edt" qui retourne le planning en JSON, se connecter à http://localhost:5000/edt 
@app.route("/edt")
def get_edt():
    if os.path.exists(ICS_FILE):
        events = parse_ics(ICS_FILE)
        return jsonify(events)
    else:
        return jsonify({"error": "Fichier ICS non trouvé"}), 404

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
