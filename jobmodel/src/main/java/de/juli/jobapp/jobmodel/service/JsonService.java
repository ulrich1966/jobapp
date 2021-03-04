package de.juli.jobapp.jobmodel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.juli.jobapp.jobmodel.model.Model;
import de.juli.jobapp.jobmodel.util.AppProperties;
import javassist.NotFoundException;

/**
 * Erstellen und auslesen von Dateien im JSON Format !!! Zieldatei muss im
 * jobmodel/target vorhanden sein! Vorsicht mit mvn clean
 */
public class JsonService<T> {
	private static final Logger LOG = LoggerFactory.getLogger(JsonService.class);
	private String jsonDir;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * Konstruktor setzt das Verzeichnis, in dem die JSON Dateien zu finden sind. Dieses sollte 
	 * in der config.xml angegeben sein. 
	 */
	public JsonService() {
		super();
		try {
			jsonDir = AppProperties.getInstance(AppProperties.CONFIG_PROP).propertyFind("json.data.setup.dir");
		} catch (NotFoundException e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * Uebernimmt ein Model-Objekt aus dem package de.juli.jobapp.jobmodel.model.Model, 
	 * castet es auf den ageggebenen Typ <T> und schreibt dessen Werte
	 * in die JSON - Datei angegebnenen Namens Der Dateiname kann mit oder ohne
	 * Suffix 'json' uebergeben werden. In createFile() wird die Existenz der Datei gepfueft 
	 * und ggf. neu erzeugt. Ergeugt einen String fuer die Rueckgabe 
	 * und giebt einen formtierten String im Logger aus. 
	 * @throws NotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public String write(Model model, String name) throws NotFoundException {
		String jsonString = null;
		try {
			mapper.writeValue(createFile(name), (T) model);
			//jsonString = mapper.writeValueAsString(model);
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
			LOG.debug(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	/**
	 * Uebernimmt eine Liste mit Model-Objekten aus de.juli.jobapp.jobmodel.model und einen 
	 * Dateinamen in dem die Daten hiterlegt sein sollten. Die Existenz der Datei wird in crateFile 
	 * geprueft und ggt. wird sie dort angelegt. 
	 * Dann werden die in der Liste befindlichen Objekte in die Datei gemappt. Das Zielverzeichnis 
	 * ligt dann im jobmodel/target, also Vorsicht mit mvn clean!!!  
	 */
	@SuppressWarnings("unchecked")
	public String write(List<T> list, String name) throws NotFoundException {
		String jsonString = null;
		try {
			mapper.writeValue(createFile(name), (T) list);
			jsonString = mapper.writeValueAsString(list);
			String prettyJson= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
			LOG.debug(prettyJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	/**
	 * Parst JSON aus einer Datei des uebergebenen Namens und git ein Model-Type des in der 
	 * Klasse angebebenen Typs zurueck.  
	 */
	public T read(Class<T> clazz, String name) throws NotFoundException {
		T result = null;
		try {
			result = mapper.readValue(createFile(name), clazz);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Parst JSON aus einer Datei des angegebenen Namens und git ein Model-Objekt des in der 
	 * Klasse angebebenen Typs zurueck. Da die in einem Unterverzueichnis von jobmodel/target 
	 * liegt, ist mit mvn clean vorsichtigt umzugehen. Die Datei wird zwar in createFile(name)
	 * ezeugt, ist dann aber leer.  
	 */
	public Model read(Model model, String name) throws ClassNotFoundException, NotFoundException {
		Model result = null;
		try {
			result = mapper.readValue(createFile(name), model.getClass());
			if(result == null) {
				throw new ClassNotFoundException(String.format("Klasse nicht gefunden: %s", model.getClass().getSimpleName()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * Parst JSON aus einer Datei des angegebenen Namens und git ein eine Liste von Model-Objekten
	 * des in der Klasse angebebenen Typs zurueck. Da die in einem Unterverzueichnis von jobmodel/target 
	 * liegt, ist mit mvn clean vorsichtigt umzugehen. Die Datei wird zwar in createFile(name)
	 * ezeugt, ist dann aber leer.    
	 */
	public List<T> readList(Class<T> clazz, String name) {
		List<T> result = null;
		try {
			result = mapper.readerForListOf(clazz).readValue(createFile(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	
	/**
	 * Bildet die Url aus dem ubergebnen name, haengt ggf. das Suffix '.json'
	 * anund holt den Resourcepfad (!!! Zieldatei muss im jobmodel/target
	 * vorhanden sein! Vorsicht mit mvn clean ) Prueft die Existenz und
	 * generiert ein File-Objekt, das zurueckgegeben wird.
	 * @throws IOException 
	 * @throws NotFoundException 
	 */
	private File createFile(String name) throws IOException {
		URL dirUrl = this.getClass().getResource(String.format("/%s/", this.jsonDir));
		String file$ = null;			
		File file = null;

		if (dirUrl == null) {
			throw new FileNotFoundException(String.format("Das Verzeichnis [%s] existiert nicht, hier kann nichts gespeichert werden!\n Vielleicht hilft ein mvn package weiter?", dirUrl));
		}
		if (!name.contains(".json")) {
			name = String.format("%s%s", name, ".json");
		}
		URL resourceUrl = this.getClass().getResource(String.format("/%s/%s", dirUrl, name));
		if (resourceUrl == null) {
			file = new File(String.format("/%s/%s", dirUrl.getFile(), name));
			file.createNewFile();
			
		} else {
			file$ = resourceUrl.getFile();
			file = new File(file$);
		}
		LOG.debug("Akruelle Datei: {}", file.getPath());
		return file;
	}



}
