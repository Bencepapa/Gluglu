package gluglu;

import java.io.PrintStream;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

// Referenced classes of package gluglu:
//            Score, Language, GlugluCanvas

public class Gluglu extends MIDlet
    implements CommandListener
{

    static Gluglu instance;
    int palya;
    private int Lang;
    private Language Lg;
    private Score sc;
    Display display;
    GlugluCanvas canvas;
    private Command exitCommand;
    private Command cancelCommand;
    private Command toggleCommand;
    private Command okCommand;
    private Command ok2Command;
    private Command ok3Command;
    private Command toggleCommand2;
    private Command palyaCommand;
    private Command helpCommand;
    private Command aboutCommand;
    private Command modCommand;
    private Command langCommand;
    private Command okLangCommand;
    private Form helpScreen;
    private Form aboutScreen;
    private Form optionScreen;
    private Form palyaScreen;
    private Form langScreen;
    private TextBox levelText;
    Screen levelScreen;
    ChoiceGroup cg1;
    ChoiceGroup cg2;
    Gauge gauge;
    boolean scratch[];

    public Gluglu()
    {
        palya = 1;
        Lang = 0;
        System.out.println("Kezd\351s");
        instance = this;
        display = Display.getDisplay(this);
        sc = new Score();
        if(!sc.open())
        {
            System.out.println("Score open failed");
        }
        Lang = sc.getTheme();
        System.out.println("Lang=" + Lang);
        if(Lang == 0)
        {
            Lg = new Language();
        } else
        {
            Lg = new Language(Lang);
        }
        Lang = Lg.getLanguage();
        canvas = new GlugluCanvas(display, Lg, sc);
        setCommands();
        addCommands();
        canvas.setCommandListener(this);
    }

    public void setCommands()
    {
        exitCommand = new Command(Lg.commExit(), 7, 1);
        cancelCommand = new Command(Lg.commBack(), 3, 2);
        toggleCommand = new Command(Lg.commNew(), 1, 1);
        okCommand = new Command(Lg.commOk(), 1, 1);
        ok2Command = new Command(Lg.commDone(), 1, 1);
        ok3Command = new Command(Lg.commDone(), 1, 1);
        toggleCommand2 = new Command(Lg.commNumbOfGluglu(), 1, 2);
        palyaCommand = new Command(Lg.commLevel(), 1, 2);
        helpCommand = new Command(Lg.commHelp(), 5, 2);
        aboutCommand = new Command("About", 5, 2);
        modCommand = new Command(Lg.commAppearance(), 1, 2);
        langCommand = new Command("Language", 1, 2);
        okLangCommand = new Command(Lg.commDone(), 1, 1);
    }

    public void addCommands()
    {
        canvas.addCommand(exitCommand);
        canvas.addCommand(cancelCommand);
        canvas.addCommand(toggleCommand);
        canvas.addCommand(toggleCommand2);
        canvas.addCommand(palyaCommand);
        canvas.addCommand(helpCommand);
        canvas.addCommand(aboutCommand);
        if(display.isColor())
        {
            canvas.addCommand(modCommand);
        }
        canvas.addCommand(aboutCommand);
        canvas.addCommand(langCommand);
    }

    public void removeCommands()
    {
        canvas.removeCommand(exitCommand);
        canvas.removeCommand(cancelCommand);
        canvas.removeCommand(toggleCommand);
        canvas.removeCommand(toggleCommand2);
        canvas.removeCommand(palyaCommand);
        canvas.removeCommand(helpCommand);
        canvas.removeCommand(aboutCommand);
        if(display.isColor())
        {
            canvas.removeCommand(modCommand);
        }
        canvas.removeCommand(aboutCommand);
        canvas.removeCommand(langCommand);
    }

    public void changeMenu()
    {
        removeCommands();
        setCommands();
        addCommands();
    }

    public void startApp()
        throws MIDletStateChangeException
    {
        canvas.start();
    }

    public void pauseApp()
    {
        canvas.pause();
    }

    public void destroyApp(boolean unconditional)
        throws MIDletStateChangeException
    {
        canvas.destroy();
        if(sc != null)
        {
            sc.close();
        }
    }

    public void commandAction(Command c, Displayable s)
    {
        if(canvas.rajz == 1)
        {
            canvas.rajz = 0;
        }
        if(c == okCommand)
        {
            display.setCurrent(canvas);
        } else
        if(c == ok2Command)
        {
            canvas.Mod = (byte)(cg1.getSelectedIndex() + 1);
            display.setCurrent(canvas);
        } else
        if(c == ok3Command)
        {
            canvas.setLevel((byte)(gauge.getValue() + 1));
            canvas.score = 0;
            canvas.Inicializal();
            display.setCurrent(canvas);
            canvas.start();
        } else
        if(c == toggleCommand && s == levelScreen)
        {
            int l = Integer.parseInt(levelText.getString());
            if(canvas.maxGolyo != l && l <= 8 && l >= 3)
            {
                canvas.maxGolyo = (byte)l;
                canvas.setLevel(1);
                canvas.score = 0;
                canvas.Inicializal();
            }
            display.setCurrent(canvas);
            canvas.start();
        } else
        if(c == toggleCommand)
        {
            canvas.start();
            canvas.setLevel(1);
            canvas.score = 0;
            canvas.Inicializal();
        } else
        if(c == toggleCommand2)
        {
            canvas.start();
            levelScreen = getLevelScreen();
            levelScreen.addCommand(toggleCommand);
            levelScreen.setCommandListener(this);
            display.setCurrent(levelScreen);
        } else
        if(c == modCommand)
        {
            canvas.pause();
            Options();
        } else
        if(c == palyaCommand)
        {
            canvas.pause();
            showPalyaValasztas();
        } else
        if(c == helpCommand)
        {
            canvas.pause();
            showHelp();
        } else
        if(c == exitCommand)
        {
            try
            {
                destroyApp(false);
                notifyDestroyed();
            }
            catch(MIDletStateChangeException ex) { }
        } else
        if(c == aboutCommand)
        {
            canvas.pause();
            showAbout();
        } else
        if(c == langCommand)
        {
            canvas.pause();
            showLang();
        } else
        if(c == okLangCommand)
        {
            Lang = (byte)cg2.getSelectedIndex();
            Lg.setLanguage(Lang);
            sc.setLevel(canvas.palya, Lang);
            helpScreen = null;
            levelText = null;
            optionScreen = null;
            langScreen = null;
            palyaScreen = null;
            aboutScreen = null;
            changeMenu();
            display.setCurrent(canvas);
        }
    }

    void showHelp()
    {
        if(helpScreen == null)
        {
            helpScreen = new Form(Lg.HelpTitle());
            helpScreen.append(Lg.HelpControll());
            helpScreen.append(Lg.HelpRules());
            helpScreen.append(Lg.Help1() + Lg.Help2() + Lg.Help3());
            helpScreen.append(Lg.Help4());
            helpScreen.append(Lg.Help5() + " " + Lg.Help6() + " " + Lg.Help7() + " " + Lg.Help8());
        }
        helpScreen.addCommand(okCommand);
        helpScreen.setCommandListener(this);
        display.setCurrent(helpScreen);
    }

    void showAbout()
    {
        if(aboutScreen == null)
        {
            aboutScreen = new Form("Gluglu v1.4");
            aboutScreen.append("(C) 2004-2006 Bence Dobos");
            aboutScreen.append("German and Finnish translation: Erhard Lang");
            aboutScreen.append("\nWAP: bazalt.try.hu");
            aboutScreen.append("Html: bazalt.uw.hu");
            aboutScreen.append("E-mail: alberton@freemail.hu");
        }
        aboutScreen.addCommand(okCommand);
        aboutScreen.setCommandListener(this);
        display.setCurrent(aboutScreen);
    }

    public Screen getLevelScreen()
    {
        if(levelText == null)
        {
            levelText = new TextBox(Lg.howManyGluglu(), Integer.toString(canvas.maxGolyo), 1, 2);
        } else
        {
            levelText.setString(Integer.toString(canvas.maxGolyo));
        }
        return levelText;
    }

    void Options()
    {
        if(optionScreen == null)
        {
            optionScreen = new Form(Lg.commAppearance());
            optionScreen.append(Lg.chooseAppearance());
            cg1 = new ChoiceGroup(null, 1);
            cg1.append(Lg.commAppearanceSpheres(), null);
            cg1.append(Lg.commAppearanceDisks(), null);
            cg1.append(Lg.commAppearanceContrast(), null);
            cg1.append(Lg.commAppearanceFigures(), null);
            cg1.setSelectedIndex(canvas.Mod - 1, true);
            optionScreen.append(cg1);
        }
        optionScreen.addCommand(ok2Command);
        optionScreen.setCommandListener(this);
        display.setCurrent(optionScreen);
    }

    void showLang()
    {
        if(langScreen == null)
        {
            langScreen = new Form("Language");
            langScreen.append("Choose language:");
            cg2 = new ChoiceGroup(null, 1);
            cg2.append("English", null);
            cg2.append("Hungarian", null);
            cg2.append("German", null);
            cg2.append("Finnish", null);
            cg2.setSelectedIndex(Lang, true);
            langScreen.append(cg2);
            String value = System.getProperty("microedition.locale");
        }
        langScreen.addCommand(okLangCommand);
        langScreen.setCommandListener(this);
        display.setCurrent(langScreen);
    }

    void showPalyaValasztas()
    {
        if(palyaScreen == null)
        {
            palyaScreen = new Form(Lg.fullLevel());
            palyaScreen.append(Lg.chooseLevel());
            gauge = new Gauge(Lg.fullLevel(), true, 49, canvas.palya - 1);
            palyaScreen.append(gauge);
        }
        palyaScreen.addCommand(ok3Command);
        palyaScreen.setCommandListener(this);
        display.setCurrent(palyaScreen);
    }
}
