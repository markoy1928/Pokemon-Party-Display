import java.util.Vector;

public class Pokemon {
    private int pid;
    private int dexNo;
    private String species;
    private String nickname;
    private char gender;
    private String form;
    private int hp;
    private int maxHP;
    private int level;
    private Status status;
    private Item item;
    private boolean egg;
    private Move[] moves;

    public Pokemon(int pid, int dex, String name, int gender, int form, int hp, int maxHP, int level, int status, int item, boolean egg, Move[] moves) {
        this.pid = pid;
        this.dexNo = dex;
        this.nickname = name;
        this.gender = numToGender(gender);
        this.form = setForm(form);
        this.hp = hp;
        this.maxHP = maxHP;
        this.level = level;
        this.status = numToStatus(status);
        this.item = new Item(item);
        this.egg = egg;
        this.moves = moves;
        this.species = setSpecies();
    }

    private String setForm(int form) {
        PokeDB db = new PokeDB();
        Vector<String> forms = db.select("Pokemon", "form", "dex = " + dexNo);

        if (forms.isEmpty() || (forms.get(0) == null && forms.size() == 1)) {
            return null;
        }

        // Gen 5- sprites come from PokemonDB and Gen6+ sprites come from Pokemon Wiki

        // Megas
        if (forms.get(0) == null && forms.get(1).equals("Mega")) {
            // TODO: Megas
            return null;
        }
        // Genders
        else if (forms.get(0) != null && (forms.get(0).equals("Male") || forms.get(0).equals("Female"))) {
            // TODO: Find values for male and female to stop using sql forms
            if (gender == 'M') {
                return "Male";
            }
            else {
                return "Female";
            }
        }
        // Individual Pokemon
        else {
            switch (dexNo) {
                // Unown
                case 201:
                    return String.valueOf((form - 4) / 8);

                // Castform
                case 351:
                    return String.valueOf((form - 2) / 8);
            
                // Deoxys
                case 386:
                    return String.valueOf((form - 4) / 8);
                    
                // Burmy
                case 412:
                    return String.valueOf((form - 2) / 8);

                // Wormadam
                case 413:
                    return String.valueOf((form - 2) / 8);

                // Cherrim
                case 421:
                    return String.valueOf((form - 2) / 8);

                // Shellos
                case 422:
                    return String.valueOf((form - 2) / 8);

                // Gastrodon
                case 423:
                    return String.valueOf((form - 2) / 8);
                    
                // Rotom
                case 479:
                    return String.valueOf((form - 4) / 8);

                // Giratina
                case 487:
                    return String.valueOf((form - 4) / 8);

                // Shaymin
                case 492:
                    return String.valueOf((form - 4) / 8);

                // Arceus
                case 493:
                    return String.valueOf((form - 4) / 8);

                // Basculin
                case 550:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Darmanitan
                case 555:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Deerling
                case 585:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Sawsbuck
                case 586:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Tornadus
                case 641:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Thundurus
                case 642:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Landorus
                case 645:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Kyurem
                case 646:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Keldeo
                case 647:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Meloetta
                case 648:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Genesect
                case 649:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Vivillon
                case 666:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Flabébé
                case 669:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Floette
                case 670:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Florges
                case 671:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Furfrou
                case 676:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Meowstic
                case 678:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Aegislash
                case 681:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Pumpkaboo
                case 710:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Gourgeist
                case 711:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Xerneas
                case 716:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                // Hoopa
                case 720:
                    // TODO
                    return String.valueOf((form - 4) / 8);

                default:
                    return null;
            }
        }
    }

    public String getNickname() {
        return nickname;
    }

    public int getHP() {
        return hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getLevel() {
        return level;
    }

    public Status getStatus() {
        return status;
    }

    public Move[] getMoves() {
        return moves;
    }

    public int getDex() {
        return dexNo;
    }

    public char getGender() {
        return gender;
    }

    public boolean isEgg() {
        return egg;
    }

    public Item getItem() {
        return item;
    }

    public int getPID() {
        return pid;
    }

    public String setSpecies() {
        // Query database for Pokemon Species Name
        PokeDB db = new PokeDB();
        Vector<String> sps = db.select("Pokemon", "species", "dex = " + dexNo);

        if (sps.isEmpty()) {
            return null;
        }

        String s = sps.get(0);

        // Fix Nidoran M/F
        if (s != null && s.equals("Nidoran")) {
            if (gender == 'M') {
                return s + '\u2642';
            }
            else {
                return s + '\u2640';
            }
        }
        else {
            return s;
        }
    }

    public String getSpecies() {
        return species;
    }

    public String getForm() {
        return form;
    }

    public String getFilePath() {
        String gif;
        String sp = species;

        // Fix path for Nidoran M/F
        if (dexNo == 29 || dexNo == 32) {
            sp = "Nidoran";
        }

        if (egg) {
            gif = "egg.gif";
        }
        else {
            if (species == null) {
                gif = null;
            }
            else if (form == null) {
                gif = sp.toLowerCase() + ".gif";
            }
            else {
                gif = sp.toLowerCase() + "-" + form.toLowerCase() + ".gif";
            }
        }

        return "Pokemon/" + gif; 
    }

    public enum Status {
        HEALTHY,
        SLEEPING,
        POISONED,
        BURNED,
        FROZEN,
        PARALYZED,
        BADLY_POISONED;
    }

    public boolean hasItem() {
        return item.exists();
    }

    // Determine if 2 Pokemon are equal
    @Override
    public boolean equals(Object other) {
        // Make sure neither is null
        if (this == null || other == null) {
            return false;
        }

        // Make sure other is a pokemon
        if (!(other instanceof Pokemon)) {
            return false;
        }

        // Call it a pokemon
        Pokemon otherPK = (Pokemon)other;

        // Dex
        boolean dexEQ = this.dexNo == otherPK.getDex();

        // Species
        boolean speciesEQ;
        if (this.species == null && otherPK.getSpecies() == null) {
            speciesEQ = true;
        }
        else if (this.species == null || otherPK.getSpecies() == null) {
            speciesEQ = false;
        }
        else {
            speciesEQ = this.species.equals(otherPK.getSpecies());
        }

        // Nickname
        boolean nicknameEQ;
        if (this.nickname == null && otherPK.getNickname() == null) {
            nicknameEQ = true;
        }
        else if (this.nickname == null || otherPK.getNickname() == null) {
            nicknameEQ = false;
        }
        else {
            nicknameEQ = this.nickname.equals(otherPK.getNickname());
        }

        // Check everything else
        boolean pidEQ = this.pid == otherPK.getPID();
        boolean genderEQ = this.gender == otherPK.getGender();
        boolean levelEQ = this.level == otherPK.getLevel();
        boolean hpEQ = this.hp == otherPK.getHP() && this.maxHP == otherPK.getMaxHP();
        boolean statusEQ = this.status == otherPK.getStatus();
        boolean eggEQ = this.egg == otherPK.isEgg();
        boolean itemEQ = this.item.equals(otherPK.getItem());
        boolean movesEQ = this.moves[0].equals(otherPK.getMoves()[0]) && this.moves[1].equals(otherPK.getMoves()[1]) && this.moves[2].equals(otherPK.getMoves()[2]) && this.moves[3].equals(otherPK.getMoves()[3]);

        return pidEQ && dexEQ && speciesEQ && nicknameEQ && genderEQ && levelEQ && hpEQ && statusEQ && eggEQ && movesEQ && itemEQ;
    }

    private char numToGender(int gender) {
        if (gender == 0) {
            return 'M';
        }
        else if (gender == 1) {
            return 'F';
        }
        else {
            return 0;
        }
    }

    private Status numToStatus(int statusNo) {
        switch (statusNo) {
            case 1:
                return Status.SLEEPING;
        
            case 2:
                return Status.SLEEPING;
        
            case 3:
                return Status.SLEEPING;
        
            case 4:
                return Status.SLEEPING;
        
            case 5:
                return Status.SLEEPING;
        
            case 6:
                return Status.SLEEPING;
        
            case 7:
                return Status.SLEEPING;
        
            case 8:
                return Status.POISONED;

            case 16:
                return Status.BURNED;

            case 32:
                return Status.FROZEN;

            case 64:
                return Status.PARALYZED;

            case 128:
                return Status.BADLY_POISONED;

            default:
                return Status.HEALTHY;
        }
    }
}
