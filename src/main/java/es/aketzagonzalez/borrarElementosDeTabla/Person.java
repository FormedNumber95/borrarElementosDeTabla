package es.aketzagonzalez.borrarElementosDeTabla;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase Person.
 */
public class Person {
    
    /** El personSequence. */
    private static AtomicInteger personSequence = new AtomicInteger(0);
    
    /** El id de la persona. */
    private int personId;
    
    /** El nombre de la persona. */
    private String firstName;
    
    /** El apellido de la persona */
    private String lastName;
    
    /** La fecha de nacimiento de la persona. */
    private LocalDate birthDate;

    /**
     * El Enum AgeCategory para decir en que fase de la vida esta la persona.
     */
    public enum AgeCategory {
        
        /** Bebe. */
        BABY, 
 /** Ninio. */
 CHILD, 
 /** Adolescente. */
 TEEN, 
 /** Adulto. */
 ADULT, 
 /** Anciano. */
 SENIOR, 
 /** Desconocido. */
 UNKNOWN
    };

    /**
     * Constructor sin parametros que pone el nombre, el apellido y 
     * la fecha de nacimiento de la persona a null.
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * Constructor que recibe parametros para el nombre, el apellido y 
     * la fecha de nacimiento de la persona.
     *
     * @param firstName the first name
     * @param lastName the last name
     * @param birthDate the birth date
     */
    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    /**
     * Getter del id.
     *
     * @return El id 
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Setter del id.
     *
     * @param personId El nuevo id 
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     * Getter del nombre.
     *
     * @return -el nombre
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter del nombre.
     *
     * @param firstName El nuevo nombre
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter del apellido.
     *
     * @return El apellido
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter del apellido.
     *
     * @param lastName El nuevo apellido
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter de la fecha de nacimiento.
     *
     * @return La fecha de nacimiento
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Setter de la fehca de nacimiento.
     *
     * @param birthDate La nueva fehca de nacimiento
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Verifica si la fehca de nacimiento es valida.
     *
     * @param bdate La fehca de nacimento
     * @return true si es valida
     */
    public boolean isValidBirthDate(LocalDate bdate) {
        return isValidBirthDate(bdate, new ArrayList<>());
    }

    /**
     * Verifica si la fehca de nacimiento es valida.
     *
     * @param bdate La fehca de nacimento
     * @param errorList La lista de errores
     * @return true si es valida
     */
    public boolean isValidBirthDate(LocalDate bdate, List<String> errorList) {
        if (bdate == null) {
            return true;
        }
        // Birth date cannot be in the future
        if (bdate.isAfter(LocalDate.now())) {
            errorList.add("Birth date must not be in future.");
            return false;
        }
        return true;
    }

    /**
     * Verifica si la persona es valida.
     *
     * @param errorList La lista de errores
     * @return true si es valida
     */
    public boolean isValidPerson(List<String> errorList) {
        return isValidPerson(this, errorList);
    }

    /**
     * Verifica si la persona es valida.
     *
     * @param p La persona
     * @param errorList La lista de errores
     * @return true si es valida
     */
    public boolean isValidPerson(Person p, List<String> errorList) {
        boolean isValid = true;
        String fn = p.getFirstName();
        if (fn == null || fn.trim().length() == 0) {
            errorList.add("First name must contain minimum one character.");
            isValid = false;
        }
        String ln = p.getLastName();
        if (ln == null || ln.trim().length() == 0) {
            errorList.add("Last name must contain minimum one character.");
            isValid = false;
        }
        if (!isValidBirthDate(this.getBirthDate(), errorList)) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Getter de la categoria.
     *
     * @return La categoria de edad
     */
    public AgeCategory getAgeCategory() {
        if (birthDate == null) {
            return AgeCategory.UNKNOWN;
        }
        long years = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (years >= 0 && years < 2) {
            return AgeCategory.BABY;
        } else if (years >= 2 && years < 13) {
            return AgeCategory.CHILD;
        } else if (years >= 13 && years <= 19) {
            return AgeCategory.TEEN;
        } else if (years > 19 && years <= 50) {
            return AgeCategory.ADULT;
        } else if (years > 50) {
            return AgeCategory.SENIOR;
        } else {
            return AgeCategory.UNKNOWN;
        }
    }

    /**
     * Guardar.
     *
     * @param errorList La lista de errores
     * @return true si lo ha conseguido
     */
    public boolean save(List<String> errorList) {
        boolean isSaved = false;
        if (isValidPerson(errorList)) {
            System.out.println("Saved " + this.toString());
            isSaved = true;
        }
        return isSaved;
    }

    /**
     * El toString de la persona.
     *
     * @return como se visualiza una persona si se le hace un print
     */
    @Override
    public String toString() {
        return "[personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + "]";
    }
}
