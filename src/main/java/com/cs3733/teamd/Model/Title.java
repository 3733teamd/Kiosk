package com.cs3733.teamd.Model;


public enum Title {
    RN ("RN", "Advanced Practice Nurse"),
    CPNP ("CPNP", "Certified Pediatric Nurse Practioner"),
    MS ("MS", "Master of Science"),
    MD ("MD", "Doctor of Medicine"),
    DNP ("DNP", "Doctor of Nurse Practicing"),
    DO ("DO", "Doctor of Osteopathic Medicine"),
    WHNP ("WHNP", "Women`s Health Nurse Practitioner"),
    PAC ("PA-C", "Physician Assistant Certified"),
    AuD ("Au.D", "Doctor of Audiology"),
    DPM ("DPM", "Doctor of Podiatric Medicine"),
    CCCA ("CCC-A", "Comprehensive Care Center"),
    LDN ("LDN", "Low Dose Naltrexone"),
    PhD ("PhD", "Doctor"),
    LICSW ("LICSW", "Licensed Clinical Social Worker"),
    RD ("RD", "Registered Dietitian"),
    NP ("NP", "Nurse Practitioner"),
    PsyD ("PsyD", "Doctor of Psychology"),
    ABPP ("ABPP", "American Board of Professional Psychology");
    String acronym;
    String fullTitle;
    int id;
    Title(String acronym, String fullTitle){
        this.acronym = acronym;
        this.fullTitle = fullTitle;
    }
    @Override
    public String toString(){
        return acronym;
    }
}
