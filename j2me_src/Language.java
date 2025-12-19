package gluglu;

import java.io.PrintStream;

public class Language
{

    private int aktLang;
    private static int defaultLanguage = 1;
    private static String commExit[] = {
        "Exit", "Kil\351p\351s", "Ausgang", "Ulosp\344\344sy"
    };
    private static String commBack[] = {
        "Back", "Vissza", "Zur\374ck", "Takaisin"
    };
    private static String commNew[] = {
        "New", "\332j", "Neu", "Uusi"
    };
    private static String commOk[] = {
        "Okay", "Ok\351", "Okay", "Oikein"
    };
    private static String commDone[] = {
        "Done", "K\351sz", "Fertig", "Loppu"
    };
    private static String commLevel[] = {
        "Level", "P\341lya", "Level", "Taso"
    };
    private static String commHelp[] = {
        "Help", "Seg\355ts\351g", "Anweisungen", "Ohjeet"
    };
    private static String commAppearance[] = {
        "Appearance", "Megjelen\351s", "Bildform", "Kuvio"
    };
    private static String chooseAppearance[] = {
        "Choose appearance", "V\341laszz megjelen\351st", "W\344hle Bildform", "Valitse kuvio"
    };
    private static String commAppearanceSpheres[] = {
        "Spheres", "Goly\363k", "B\344lle", "Pallot"
    };
    private static String commAppearanceDisks[] = {
        "Disks", "Korongok", "Kreise", "Py\366r\344t"
    };
    private static String commAppearanceContrast[] = {
        "Contrast", "Kontrasztos", "Kontrast", "Kontrasti"
    };
    private static String commAppearanceFigures[] = {
        "Figures", "Figur\341k", "Figuren", "S\344rmikk\344\344t"
    };
    private static String chooseLevel[] = {
        "Choose level:", "V\341lassz p\341ly\341t:", "W\344hle Schwierigkeitsgrad:", "Valitse taso:"
    };
    private static String howManyGluglu[] = {
        "How many gluglu:", "H\341ny goly\363 legyen:", "Wie viele Gluglu:", "Paljonko glugluja:"
    };
    private static String commNumbOfGluglu[] = {
        "Number of gluglu", "Goly\363k sz\341ma", "Anzahl der B\344lle", "pallojen m\344\344r\344"
    };
    private static String fullLevel[] = {
        "Level", "P\341lya", "Level", "Taso"
    };
    private static String Level[] = {
        "Lv", "P\341", "Lv", "Ta"
    };
    private static String miniLevel[] = {
        "L:", "P:", "L:", "T:"
    };
    private static String fullBall[] = {
        "Ball", "Goly\363", "B\344lle", "Pallot"
    };
    private static String Ball[] = {
        "Ba", "Go", "B\344", "Pa"
    };
    private static String miniBall[] = {
        "B:", "G:", "B:", "P:"
    };
    private static String fullScore[] = {
        "Score", "Pont", "Punkte", "Pisteesi"
    };
    private static String Score[] = {
        "Sc", "Po", "Pu", "Pi"
    };
    private static String miniScore[] = {
        "$:", "$:", "$:", "$:"
    };
    private static String HelpTitle[] = {
        "Gluglu Help", "Gluglu S\372g\363", "Gluglu Hilfe", "Gluglu Avustus"
    };
    private static String HelpControll[] = {
        "Controll:\n^ (2)= up\nv (8)= down\n< (4)= left\n> (6)= right\nfire (5)= roll\n", "\315r\341ny\355t\341s:\n^ (2)= fel\nv (8)= le\n< (4)= balra\n> (6)= jobbra\nt\u0171" +
"z (5)= gur\355t\341s\n"
, "Kontrolle:\n^ (2)= nach oben\nv (8)= nach unten\n< (4)= nach links\n> (6)= nach " +
"rechts\nfeuer (5)= Klucker rollen\n"
, "S\344\344d\366t:\n^ (2)= yl\366s\nv (8)= alas\n< (4)= vasemmalle\n> (6)= oikeall" +
"e\nfire (5)= rullauta pallo\n"

    };
    private static String HelpRules[] = {
        "Rules:", "Szab\341lyok:", "Spielregeln:", "Pelis\344\344nn\366t:"
    };
    private static String Help1[] = {
        "The game's goal is to clear the table. ", "A j\341t\351k c\351lja a t\341bl\341r\363l az \366sszes goly\363 elt\374ntet\351" +
"se. "
, "Ziel des Spiels ist es, den Tisch abzur\344umen. ", "Pelin tarkoitus on tyhjent\344\344 p\366yt\344. "
    };
    private static String Help2[] = {
        "You can remove ball, ", "Goly\363t \372gy lehet levenni, ", "Ein Ball wird getilgt, ", "Pallo poistetaan, "
    };
    private static String Help3[] = {
        "just roll to a neighbour cell another ball with same color.", "hogy szomsz\351dos mez\u0151be gur\355tunk azonos sz\355n\u0171 goly\363t.", "lass einen anderen Ball derselben Farbe auf eine Nachbarzelle rollen.", "anna toisen samanv\344risen pallon rullata sen viereiseen rastiin."
    };
    private static String Help4[] = {
        "You can remove two or more balls of the same color at once.", "Egyszerre t\366bb azonos sz\355n\u0171 goly\363 is levehet\u0151.", "Du kannst zwei oder mehr B\344lle der gleichen Farbe zugleich tilgen.", "Voit poistaa kaksi tai enemm\344n samanv\344risi\344 palloja yhteen vetoon."
    };
    private static String Help5[] = {
        "If all the balls are vanished, a new level is beginning.", "Ha az \366sszes goly\363 elt\u0171nt, \372j p\341lya kezd\u0151dik.", "Wenn alle B\344lle entfernt sind, beginnt eine neues Level.", "Kaikki pallot poistettuasi uusi taso on alkamassa."
    };
    private static String Help6[] = {
        "There are 50 levels.", "50 p\341lya van.", "Es gibt 50 Levels.", "On olemassa 50 tasoa."
    };
    private static String Help7[] = {
        "If you complete all the 50 levels, then you begin at the level 1, but with one m" +
"ore color."
, "Ha mind az 50 p\341ly\341t teljes\355tetted, akkor \372jra az els\u0151 p\341ly\341" +
"n kezdesz, de egy \372jabb sz\355n nehez\355ti a j\341t\351kot."
, "Wenn Du mit allen 50 Levels fertig bist, f\344ngst Du auf Level 1 wieder an, abe" +
"r mit einer Farbe mehr."
, "Kaikki tasot t\344ydennetty\344si alat taas ensimm\344iselt\344 tasolta johon li" +
"s\344t\344\344n uudenv\344rinen pala."

    };
    private static String Help8[] = {
        "You can play maximum 8 different colors, but if you play with 8 colors, then to " +
"complete one level may last hours!"
, "Legfeljebb 8 k\374l\366nb\366z\u0151 sz\355n\u0171 goly\363val lehet j\341tszani" +
", de ekkor egy nehezebb p\341lya megold\341sa ak\341r \363r\341kba telhet."
, "Du kannst mit maximal 8 verschiedenen Farben spielen, aber wenn Du mit 8 Farben " +
"spielst, kann es Stunden dauern bis ein Level fertig ist!"
, "Voit pelata enimmill\344\344n 8:lla v\344rill\344, mutta kun pelaat kaikilla kah" +
"deksalla taitaa kest\344\344 tuntikausia ennenkuin lopetat yhden tason!"

    };

    public Language()
    {
        int lang = 0;
        String l = "";
        try
        {
            l = System.getProperty("microedition.locale").substring(0, 2);
        }
        catch(Exception ex)
        {
            System.out.println("A nyelv felismer\351se k\366zben hiba: " + ex);
        }
        System.out.println("Nyelv:" + l);
        if(l == "en" || l == "EN")
        {
            lang = 1;
        }
        if(l == "hu" || l == "HU")
        {
            lang = 2;
        }
        if(l == "de" || l == "DE")
        {
            lang = 3;
        }
        if(l == "fi" || l == "FI")
        {
            lang = 4;
        }
        if(lang == 0)
        {
            lang = defaultLanguage;
        }
        aktLang = lang;
    }

    public Language(int setLang)
    {
        aktLang = setLang;
    }

    public void setLanguage(int setLang)
    {
        aktLang = setLang;
    }

    public int getLanguage()
    {
        return aktLang;
    }

    public String commExit()
    {
        return commExit[aktLang];
    }

    public String commBack()
    {
        return commBack[aktLang];
    }

    public String commNew()
    {
        return commNew[aktLang];
    }

    public String commOk()
    {
        return commOk[aktLang];
    }

    public String commDone()
    {
        return commDone[aktLang];
    }

    public String commLevel()
    {
        return commLevel[aktLang];
    }

    public String commHelp()
    {
        return commHelp[aktLang];
    }

    public String commAppearance()
    {
        return commAppearance[aktLang];
    }

    public String chooseAppearance()
    {
        return chooseAppearance[aktLang];
    }

    public String commAppearanceSpheres()
    {
        return commAppearanceSpheres[aktLang];
    }

    public String commAppearanceDisks()
    {
        return commAppearanceDisks[aktLang];
    }

    public String commAppearanceContrast()
    {
        return commAppearanceContrast[aktLang];
    }

    public String commAppearanceFigures()
    {
        return commAppearanceFigures[aktLang];
    }

    public String chooseLevel()
    {
        return chooseLevel[aktLang];
    }

    public String howManyGluglu()
    {
        return howManyGluglu[aktLang];
    }

    public String commNumbOfGluglu()
    {
        return commNumbOfGluglu[aktLang];
    }

    public String fullLevel()
    {
        return fullLevel[aktLang];
    }

    public String Level()
    {
        return Level[aktLang];
    }

    public String miniLevel()
    {
        return miniLevel[aktLang];
    }

    public String fullBall()
    {
        return fullBall[aktLang];
    }

    public String Ball()
    {
        return Ball[aktLang];
    }

    public String miniBall()
    {
        return miniBall[aktLang];
    }

    public String fullScore()
    {
        return fullScore[aktLang];
    }

    public String Score()
    {
        return Score[aktLang];
    }

    public String miniScore()
    {
        return miniScore[aktLang];
    }

    public String HelpTitle()
    {
        return HelpTitle[aktLang];
    }

    public String HelpControll()
    {
        return HelpControll[aktLang];
    }

    public String HelpRules()
    {
        return HelpRules[aktLang];
    }

    public String Help1()
    {
        return Help1[aktLang];
    }

    public String Help2()
    {
        return Help2[aktLang];
    }

    public String Help3()
    {
        return Help3[aktLang];
    }

    public String Help4()
    {
        return Help4[aktLang];
    }

    public String Help5()
    {
        return Help5[aktLang];
    }

    public String Help6()
    {
        return Help6[aktLang];
    }

    public String Help7()
    {
        return Help7[aktLang];
    }

    public String Help8()
    {
        return Help8[aktLang];
    }

}
