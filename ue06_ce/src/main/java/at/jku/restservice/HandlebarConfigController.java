package at.jku.restservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController public class HandlebarConfigController {

    public static final String RENNRADLENKER = "Rennradlenker";
    public static final String FLATBARLENKER = "Flatbarlenker";
    public static final String ALUMINIUM = "Aluminium";
    public static final String KUNSTSTOFF = "Kunststoff";
    public static final String STAHL = "Stahl";
    public static final String KETTENSCHALTUNG = "Kettenschaltung";
    public static final String KUNSTSTOFFGRIFF = "Kunststoffgriff";
    public static final String LEDERGRIFF = "Ledergriff";
    private final List<String> availableHandlebarTypes;
    private final List<String> availableMaterial;
    private final List<String> availableGearshifts;
    private final List<String> availableHandleMaterial;

    public HandlebarConfigController() {
        availableHandlebarTypes = new ArrayList<>();
        availableHandlebarTypes.add(FLATBARLENKER);
        availableHandlebarTypes.add(RENNRADLENKER);
        availableHandlebarTypes.add("Bullhornlenker");

        availableMaterial = new ArrayList<>();
        availableMaterial.add(ALUMINIUM);
        availableMaterial.add(STAHL);
        availableMaterial.add(KUNSTSTOFF);

        availableGearshifts = new ArrayList<>();
        availableGearshifts.add(KETTENSCHALTUNG);
        availableGearshifts.add("Nabenschaltung");
        availableGearshifts.add("Tretlagerschaltung");

        availableHandleMaterial = new ArrayList<>();
        availableHandleMaterial.add(LEDERGRIFF);
        availableHandleMaterial.add("Schaumstoffgriff");
        availableHandleMaterial.add(KUNSTSTOFFGRIFF);
    }

    @PostMapping("/order/{1}/{2}/{3}/{4}")
    public ResponseEntity<HandlebarConfig> order(@PathVariable("1") final String handlebarType,
        @PathVariable("2") final String handlebarMaterial,
        @PathVariable("3") final String handlebarGearshift,
        @PathVariable("4") final String handleMaterial) {

        if (!availableHandlebarTypes.contains(handlebarType)) {
            return getBadRequestResponseEntity(handlebarType, handlebarMaterial, handlebarGearshift,
                handleMaterial,
                "Configuration wrong: Type of Handlebar " + handlebarType + " is not available.");
        }
        if (!availableMaterial.contains(handlebarMaterial)) {
            return getBadRequestResponseEntity(handlebarType, handlebarMaterial, handlebarGearshift,
                handleMaterial,
                "Configuration wrong: Material " + handlebarMaterial + " is not available.");
        }
        if (!availableGearshifts.contains(handlebarGearshift)) {
            return getBadRequestResponseEntity(handlebarType, handlebarMaterial, handlebarGearshift,
                handleMaterial, "Configuration wrong: Type of gearshift " + handlebarGearshift
                    + " is not available.");
        }
        if (!availableHandleMaterial.contains(handleMaterial)) {
            return getBadRequestResponseEntity(handlebarType, handlebarMaterial, handlebarGearshift,
                handleMaterial,
                "Configuration wrong: Type of handle " + handleMaterial + " is not available.");
        }

        if (RENNRADLENKER.equalsIgnoreCase(handlebarType)) {
            if (!ALUMINIUM.equalsIgnoreCase(handlebarMaterial) && !KUNSTSTOFF
                .equalsIgnoreCase(handlebarMaterial)) {
                return getBadRequestResponseEntity(handlebarType, handlebarMaterial,
                    handlebarGearshift, handleMaterial,
                    "Configuration invalid: " + handlebarType + " is not compatible with material "
                        + handlebarMaterial);
            }
        }
        if (FLATBARLENKER.equalsIgnoreCase(handlebarType)) {
            if (!ALUMINIUM.equalsIgnoreCase(handlebarMaterial) && !KUNSTSTOFF
                .equalsIgnoreCase(handlebarMaterial)) {
                return getBadRequestResponseEntity(handlebarType, handlebarMaterial,
                    handlebarGearshift, handleMaterial,
                    "Configuration invalid: " + handlebarType + " is not compatible with material "
                        + handlebarMaterial);
            }
        }

        if (STAHL.equalsIgnoreCase(handlebarMaterial)) {
            if (!KETTENSCHALTUNG.equalsIgnoreCase(handlebarGearshift)) {
                return getBadRequestResponseEntity(handlebarType, handlebarMaterial,
                    handlebarGearshift, handleMaterial,
                    "Configuration invalid: " + handlebarMaterial
                        + " is not compatible with gearshift " + handlebarGearshift);
            }
        }

        if (KUNSTSTOFFGRIFF.equalsIgnoreCase(handleMaterial)) {
            if (!KUNSTSTOFF.equalsIgnoreCase(handlebarMaterial)) {
                return getBadRequestResponseEntity(handlebarType, handlebarMaterial,
                    handlebarGearshift, handleMaterial, "Configuration invalid: " + handleMaterial
                        + " is not compatible with material of handlebar " + handlebarMaterial);
            }
        }
        if (LEDERGRIFF.equalsIgnoreCase(handleMaterial)) {
            if (!RENNRADLENKER.equalsIgnoreCase(handlebarType)) {
                return getBadRequestResponseEntity(handlebarType, handlebarMaterial,
                    handlebarGearshift, handleMaterial, "Configuration invalid: " + handleMaterial
                        + " is not compatible with material of handlebar " + handlebarMaterial);
            }
        }

        final Random rnd = new Random();
        final HandlebarConfig handlebarConfig =
            new HandlebarConfig(BigInteger.probablePrime(8, rnd), handlebarType, handlebarMaterial,
                handlebarGearshift, handleMaterial, new Date());
        return ResponseEntity.ok(handlebarConfig);
    }

    private ResponseEntity getBadRequestResponseEntity(final String handlebarType,
        final String handlebarMaterial, final String handlebarGearshift,
        final String handleMaterial, final String errorMessage) {
        return new ResponseEntity(
            HttpStatus.BAD_REQUEST + " - " + errorMessage + " - " + handlebarType + " - "
                + handlebarMaterial + " - " + handlebarGearshift + " - " + handleMaterial,
            HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getAvailableHandlebarTypes") public List<String> getAvailableHandlebarTypes() {
        return availableHandlebarTypes;
    }

    @GetMapping("/getAvailableMaterial")
    public List<String> getAvailableMaterial(@RequestParam final String handlebarType) {
        return availableMaterial;
    }

    @GetMapping("/getAvailableGearshifts")
    public List<String> getAvailableGearshifts(@RequestParam final String handlebarMaterial) {
        return availableGearshifts;
    }

    @GetMapping("/getAvailableHandleMaterial")
    public List<String> getAvailableHandleMaterial(@RequestParam final String handlebarType,
        @RequestParam final String handlebarMaterial) {
        return availableHandleMaterial;
    }
}
