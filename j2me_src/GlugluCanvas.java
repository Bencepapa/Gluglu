package gluglu;

import java.util.Random;
import javax.microedition.lcdui.*;

// Referenced classes of package gluglu:
//            Score, Language

public class GlugluCanvas extends Canvas
    implements Runnable
{

    Display display;
    Random random;
    String dbStr;
    Thread szal;
    Language Lg;
    byte maxGolyo;
    int score;
    Score sc;
    Font font;
    int Levonas;
    byte Mod;
    boolean FF;
    byte styleStatus;
    int palya;
    int Kurzor;
    int Kurzor2;
    int gx;
    int gy;
    int gdx;
    int gdy;
    int meret;
    byte Tabla[][];
    byte Talon[][];
    int r;
    int frame;
    static int maxframe = 4;
    int xEltolas;
    int yEltolas;
    int Hezag;
    String Statusz;
    String str;
    byte rajz;
    int rx1;
    int rx2;
    int ry1;
    int ry2;
    int rr;
    int rmelyik;
    int width;
    int height;
    boolean paused;
    static int NUM_HISTORY = 8;
    long times[];
    int times_idx;
    static int szin[] = {
        0, 0x207f20, 0x7f3020, 0x7f7f20, 0x20307f, 0x6f307f, 32639, 0x3f3f3f, 0x7f7f7f
    };
    static int hatterSzin = 0x5fafff;
    static int kurzorSzin = 0x7090af;
    static int racsSzin = 0x3090bf;
    String msg;

    public GlugluCanvas(Display d, Language setLang, Score setsc)
    {
        random = new Random();
        maxGolyo = 4;
        Levonas = 0;
        Mod = 1;
        FF = false;
        palya = 1;
        Kurzor = 1;
        Kurzor2 = 4;
        meret = 9;
        Tabla = new byte[meret][meret];
        Talon = new byte[4][meret];
        r = 15;
        xEltolas = 10;
        yEltolas = 10;
        Hezag = 1;
        rajz = 0;
        times = new long[NUM_HISTORY];
        msg = null;
        display = d;
        Lg = setLang;
        sc = setsc;
        palya = sc.getLevel();
        if(palya == 0)
        {
            palya = 1;
        }
        if(!display.isColor() || FF)
        {
            hatterSzin = 0xffffff;
            Mod = 4;
            if(display.numColors() >= 4)
            {
                kurzorSzin = 0xafafaf;
                racsSzin = 0xafafaf;
            } else
            {
                kurzorSzin = 0xffffff;
                racsSzin = 0xafafaf;
            }
        }
        width = getWidth();
        height = getHeight();
        int minMeret = width >= height ? height : width;
        r = minMeret / (meret + 2);
        Hezag = (minMeret - r * (meret + 2)) / (meret + 1);
        if(Hezag == 0)
        {
            r--;
            Hezag = (minMeret - r * (meret + 2)) / (meret + 1);
        }
        xEltolas = (width - (r + Hezag) * meret) / 2;
        yEltolas = (height - (r + Hezag) * meret) / 2;
        String Vtest = "Goly\363";
        String Vtest2 = "00";
        Font defFont = Font.getDefaultFont();
        font = defFont;
        styleStatus = 0;
        if(width > height)
        {
            if(2 * (xEltolas - (r + Hezag)) > font.stringWidth(Vtest))
            {
                if(xEltolas < font.stringWidth(Vtest) + (r + Hezag) + Hezag)
                {
                    xEltolas = font.stringWidth(Vtest) + (r + Hezag) + Hezag;
                }
                styleStatus = 2;
                if(height - font.getHeight() * 6 < 0)
                {
                    Font _tmp = font;
                    font = font.getFont(defFont.getFace(), defFont.getStyle(), 8);
                }
            } else
            if(2 * (xEltolas - (r + Hezag)) > font.stringWidth(Vtest2))
            {
                if(xEltolas < font.stringWidth(Vtest2) + (r + Hezag) + Hezag)
                {
                    xEltolas = font.stringWidth(Vtest2) + (r + Hezag) + Hezag;
                }
                styleStatus = 3;
                if(height / 2 - font.getHeight() * 4 < 0)
                {
                    Font _tmp1 = font;
                    font = font.getFont(defFont.getFace(), defFont.getStyle(), 8);
                }
            }
        } else
        if(2 * (yEltolas - r * Hezag) > font.getHeight() + 1)
        {
            if(height - (yEltolas + meret * (r + Hezag) + r + Hezag) < font.getHeight() + 1)
            {
                yEltolas = r + Hezag;
            }
            styleStatus = 1;
        } else
        {
            Font _tmp2 = font;
            font = font.getFont(defFont.getFace(), defFont.getStyle(), 8);
            if(2 * (yEltolas - r * Hezag) > font.getHeight() + 1)
            {
                yEltolas = r + Hezag;
                styleStatus = 1;
            }
        }
        score = 0;
        Inicializal();
        paused = true;
    }

    public void run()
    {
        if(rajz == 10)
        {
            do
            {
                repaint();
                serviceRepaints();
            } while(rajz != 0);
        }
    }

    public void Inicializal()
    {
        initTabla();
        initTalon();
    }

    public void setLevel(int setPalya)
    {
        palya = setPalya;
        sc.setLevel(palya, Lg.getLanguage());
    }

    public void Hatter(Graphics g, int x, int y, int w, int h)
    {
        g.setColor(hatterSzin);
        g.fillRect(x, y, w, h);
    }

    public void Golyok(Graphics g, int x1, int y1, int x2, int y2)
    {
        for(int xx = x1; xx <= x2; xx++)
        {
            for(int yy = y1; yy <= y2; yy++)
            {
                if(Tabla[xx][yy] > 0 && Tabla[xx][yy] < 9)
                {
                    drawGolyoTabla(g, xx, yy, Tabla[xx][yy]);
                }
            }

        }

    }

    public void GolyoZapor(Graphics g)
    {
        drawGolyoXY(g, veletlen(width) - r, veletlen(height) - r, veletlen(8), veletlen(r) + r / 2);
    }

    public void Racs(Graphics g)
    {
        int xv = xEltolas - Hezag / 2;
        int yv = yEltolas - Hezag / 2;
        int hossz = meret * (r + Hezag) - Hezag;
        g.setColor(0);
        g.drawRect(xEltolas - Hezag / 2, yEltolas - Hezag / 2, (r + Hezag) * meret, (r + Hezag) * meret);
        for(int i = 0; i < 8; i++)
        {
            g.setColor(racsSzin);
            g.drawLine(xv + (i + 1) * (r + Hezag), yv + 1, xv + (i + 1) * (r + Hezag), yv + hossz);
            g.drawLine(xv + 1, yv + (i + 1) * (r + Hezag), xv + hossz, yv + (i + 1) * (r + Hezag));
        }

    }

    public void Talon(Graphics g, int melyik, int x1, int x2)
    {
        switch(melyik)
        {
        default:
            break;

        case 1: // '\001'
            for(int i = x1; i <= x2; i++)
            {
                drawGolyoXY(g, xEltolas + i * (r + Hezag), yEltolas - (r + Hezag), Talon[0][i], r);
            }

            break;

        case 2: // '\002'
            for(int i = x1; i <= x2; i++)
            {
                drawGolyoXY(g, xEltolas + i * (r + Hezag), yEltolas + meret * (r + Hezag), Talon[1][i], r);
            }

            break;

        case 3: // '\003'
            for(int i = x1; i <= x2; i++)
            {
                drawGolyoXY(g, xEltolas - (r + Hezag), yEltolas + i * (r + Hezag), Talon[2][i], r);
            }

            break;

        case 4: // '\004'
            for(int i = x1; i <= x2; i++)
            {
                drawGolyoXY(g, xEltolas + meret * (r + Hezag), yEltolas + i * (r + Hezag), Talon[3][i], r);
            }

            break;
        }
    }

    public void torolTalon(Graphics g, int melyik, int x1, int x2)
    {
        switch(melyik)
        {
        case 1: // '\001'
            Hatter(g, xEltolas + x1 * (r + Hezag), yEltolas - (r + Hezag), (((x2 - x1) + 1) * (r + Hezag) + Hezag) - Levonas, (r + Hezag) - Levonas);
            break;

        case 2: // '\002'
            Hatter(g, xEltolas + x1 * (r + Hezag), yEltolas + meret * (r + Hezag) + Hezag, (((x2 - x1) + 1) * (r + Hezag) + Hezag) - Levonas, (r + Hezag) - Levonas);
            break;

        case 3: // '\003'
            Hatter(g, xEltolas - (r + Hezag), yEltolas + x1 * (r + Hezag), (r + Hezag) - Levonas, (((x2 - x1) + 1) * (r + Hezag) + Hezag) - Levonas);
            break;

        case 4: // '\004'
            Hatter(g, xEltolas + meret * (r + Hezag) + Hezag, yEltolas + x1 * (r + Hezag), (r + Hezag) - Levonas, (((x2 - x1) + 1) * (r + Hezag) + Hezag) - Levonas);
            break;
        }
    }

    public void drawKurzor(Graphics g)
    {
        g.setColor(0);
        g.drawRect(xEltolas - Hezag / 2, yEltolas - Hezag / 2, (r + Hezag) * meret, (r + Hezag) * meret);
        switch(Kurzor)
        {
        case 0: // '\0'
            g.setColor(0x808080);
            g.drawRect(xEltolas - Hezag / 2, yEltolas - Hezag / 2, (r + Hezag) * meret, (r + Hezag) * meret);
            break;

        case 1: // '\001'
            drawKurzorKeret(g, xEltolas + Kurzor2 * (r + Hezag), yEltolas - (r + Hezag));
            break;

        case 2: // '\002'
            drawKurzorKeret(g, xEltolas + Kurzor2 * (r + Hezag), yEltolas + meret * (r + Hezag));
            break;

        case 3: // '\003'
            drawKurzorKeret(g, xEltolas - (r + Hezag), yEltolas + Kurzor2 * (r + Hezag));
            break;

        case 4: // '\004'
            drawKurzorKeret(g, xEltolas + meret * (r + Hezag), yEltolas + Kurzor2 * (r + Hezag));
            break;
        }
    }

    public void drawStatuszAlulra(Graphics g)
    {
        g.setColor(0);
        g.setFont(font);
        if(font.stringWidth("G: 0 P: 00 S: 00000") < width - 1)
        {
            Graphics _tmp = g;
            Graphics _tmp1 = g;
            g.drawString(Lg.miniBall() + maxGolyo + " " + Lg.miniLevel() + palya, 1, height - 1, 4 | 0x40);
            Graphics _tmp2 = g;
            Graphics _tmp3 = g;
            g.drawString(Lg.miniScore() + score, width / 2, height - 1, 4 | 0x40);
        } else
        {
            Graphics _tmp4 = g;
            Graphics _tmp5 = g;
            g.drawString(Lg.miniScore() + score, width / 2, height - 1, 1 | 0x40);
        }
    }

    public void drawStatuszBalra(Graphics g)
    {
        g.setColor(0);
        g.setFont(font);
        Graphics _tmp = g;
        Graphics _tmp1 = g;
        g.drawString(Lg.fullBall(), 1, 1, 4 | 0x10);
        Graphics _tmp2 = g;
        Graphics _tmp3 = g;
        g.drawString("  " + maxGolyo, 1, font.getHeight() + 1, 4 | 0x10);
        Graphics _tmp4 = g;
        Graphics _tmp5 = g;
        g.drawString(Lg.fullLevel(), 1, height / 2, 4 | 0x20);
        Graphics _tmp6 = g;
        Graphics _tmp7 = g;
        g.drawString("  " + palya, 1, height / 2, 4 | 0x10);
        Graphics _tmp8 = g;
        Graphics _tmp9 = g;
        g.drawString(Lg.fullScore(), 1, height - font.getHeight() - 1, 4 | 0x20);
        Graphics _tmp10 = g;
        Graphics _tmp11 = g;
        g.drawString("" + score, 1, height - 1, 4 | 0x20);
    }

    public void drawStatuszBalraRovid(Graphics g)
    {
        int sch = (score / 100) % 100;
        int scl = score % 100;
        g.setColor(0);
        g.setFont(font);
        Graphics _tmp = g;
        Graphics _tmp1 = g;
        g.drawString(Lg.Ball(), 1, 1, 4 | 0x10);
        Graphics _tmp2 = g;
        Graphics _tmp3 = g;
        g.drawString("" + maxGolyo, 1, font.getHeight() + 1, 4 | 0x10);
        Graphics _tmp4 = g;
        Graphics _tmp5 = g;
        g.drawString(Lg.Level(), 1, height / 2 - font.getHeight() / 2, 4 | 0x20);
        Graphics _tmp6 = g;
        Graphics _tmp7 = g;
        g.drawString("" + palya, 1, height / 2 - font.getHeight() / 2, 4 | 0x10);
        Graphics _tmp8 = g;
        Graphics _tmp9 = g;
        g.drawString(Lg.Score(), 1, height - 2 * font.getHeight() - 1, 4 | 0x20);
        Graphics _tmp10 = g;
        Graphics _tmp11 = g;
        g.drawString((sch >= 10 ? "" : "0") + sch, 1, height - font.getHeight() - 1, 4 | 0x20);
        Graphics _tmp12 = g;
        Graphics _tmp13 = g;
        g.drawString((scl >= 10 ? "" : "0") + scl, 1, height - 1, 4 | 0x40);
    }

    public void updateGolyo(Graphics g, int x1, int y1, int x2, int y2)
    {
        int xmin = x1 >= x2 ? x2 : x1;
        int xmax = x1 <= x2 ? x2 : x1;
        int ymin = y1 >= y2 ? y2 : y1;
        int ymax = y1 <= y2 ? y2 : y1;
        Hatter(g, xEltolas + xmin * (r + Hezag), yEltolas + ymin * (r + Hezag), ((xmax - xmin) + 1) * (r + Hezag), ((ymax - ymin) + 1) * (r + Hezag));
        Racs(g);
        Golyok(g, xmin, ymin, xmax, ymax);
    }

    public void updateGolyoLevesz(Graphics g, int x, int y, int ujr)
    {
        Hatter(g, xEltolas + x * (r + Hezag) + 1, yEltolas + y * (r + Hezag) + 1, (r + Hezag) - Levonas, (r + Hezag) - Levonas);
        Racs(g);
        drawGolyoTabla(g, x, y, Tabla[x][y] - 10, ujr);
    }

    public void updateTalon(Graphics g, int melyik)
    {
        torolTalon(g, melyik, 0, 8);
        drawKurzor(g);
        Talon(g, melyik, 0, 8);
    }

    public void paint(Graphics g)
    {
        switch(rajz)
        {
        case 3: // '\003'
        case 4: // '\004'
        case 8: // '\b'
        case 9: // '\t'
        default:
            break;

        case 0: // '\0'
            Hatter(g, 0, 0, width, height);
            Racs(g);
            Golyok(g, 0, 0, 8, 8);
            drawKurzor(g);
            Talon(g, 1, 0, 8);
            Talon(g, 2, 0, 8);
            Talon(g, 3, 0, 8);
            Talon(g, 4, 0, 8);
            switch(styleStatus)
            {
            case 1: // '\001'
                drawStatuszAlulra(g);
                break;

            case 2: // '\002'
                drawStatuszBalra(g);
                break;

            case 3: // '\003'
                drawStatuszBalraRovid(g);
                break;
            }
            break;

        case 1: // '\001'
            updateTalon(g, rmelyik);
            rajz = 0;
            break;

        case 2: // '\002'
            rajz = 0;
            switch(Kurzor)
            {
            case 1: // '\001'
                golyoLe(g, Kurzor2);
                break;

            case 2: // '\002'
                golyoFel(g, Kurzor2);
                break;

            case 3: // '\003'
                golyoJobbra(g, Kurzor2);
                break;

            case 4: // '\004'
                golyoBalra(g, Kurzor2);
                break;
            }
            if(Kurzor != 0);
            break;

        case 5: // '\005'
            if(moveto(g, gx, gy, gx + gdx, gy + gdy, Tabla[gx][gy]))
            {
                gx += gdx;
                gy += gdy;
            } else
            {
                Levesz(gx, gy, Tabla[gx][gy]);
            }
            break;

        case 6: // '\006'
            frame = 0;
            rajz = 7;
            // fall through

        case 7: // '\007'
            if(frame < maxframe)
            {
                frame++;
                Eltuntet(g, (r * (maxframe - frame)) / maxframe);
            } else
            {
                veglegEltuntet(g);
            }
            break;

        case 10: // '\n'
            GolyoZapor(g);
            break;
        }
    }

    public void keyPressed(int keyCode)
    {
        int action = getGameAction(keyCode);
        System.out.println("keyPressed: "+keyCode);        
        if(rajz == 10)
        {
            rajz = 0;
            wait(1);
            palyaLeptet();
            Inicializal();
            repaint();
        } else
        if(rajz < 5)
        {
            switch(action)
            {
            default:
                break;

            case 49: // '1'
                rajz = 0;
                break;

            case 57: // '9'
                rajz = 10;
                break;

            case 8: // '\b'
                rajz = 2;
                rmelyik = Kurzor;
                do
                {
                    repaint();
                    serviceRepaints();
                } while(rajz != 0 && rajz != 10);
                szal = new Thread(this);
                szal.start();
                break;

            case 2: // '\002'
                rmelyik = Kurzor;
                if(Kurzor == 1 || Kurzor == 2)
                {
                    Kurzor2--;
                    if(Kurzor2 < 0)
                    {
                        Kurzor2 = meret - 1;
                    }
                } else
                if(Kurzor == 3 || Kurzor == 4)
                {
                    Kurzor = 0;
                    Kurzor2 = 4;
                } else
                {
                    Kurzor = 3;
                    rmelyik = Kurzor;
                }
                rajz = 1;
                break;

            case 5: // '\005'
                rmelyik = Kurzor;
                if(Kurzor == 1 || Kurzor == 2)
                {
                    Kurzor2++;
                    if(Kurzor2 > meret - 1)
                    {
                        Kurzor2 = 0;
                    }
                } else
                if(Kurzor == 3 || Kurzor == 4)
                {
                    Kurzor = 0;
                    Kurzor2 = 4;
                } else
                {
                    Kurzor = 4;
                    rmelyik = Kurzor;
                }
                rajz = 1;
                break;

            case 1: // '\001'
                rmelyik = Kurzor;
                if(Kurzor == 3 || Kurzor == 4)
                {
                    Kurzor2--;
                    if(Kurzor2 < 0)
                    {
                        Kurzor2 = 8;
                    }
                } else
                if(Kurzor == 1 || Kurzor == 2)
                {
                    Kurzor = 0;
                    Kurzor2 = 4;
                } else
                {
                    Kurzor = 1;
                    rmelyik = Kurzor;
                }
                rajz = 1;
                break;

            case 6: // '\006'
                rmelyik = Kurzor;
                if(Kurzor == 3 || Kurzor == 4)
                {
                    Kurzor2++;
                    if(Kurzor2 > meret - 1)
                    {
                        Kurzor2 = 0;
                    }
                } else
                if(Kurzor == 1 || Kurzor == 2)
                {
                    Kurzor = 0;
                    Kurzor2 = 4;
                } else
                {
                    Kurzor = 2;
                    rmelyik = Kurzor;
                }
                rajz = 1;
                break;
            }
            repaint();
        }
    }

    public void palyaLeptet()
    {
        setLevel(++palya);
        if(palya >= 50)
        {
            maxGolyo++;
            if(maxGolyo >= 8)
            {
                maxGolyo = 8;
            }
            setLevel(1);
        }
    }

    void destroy()
    {
    }

    boolean isPaused()
    {
        return paused;
    }

    void pause()
    {
        if(!paused)
        {
            paused = true;
        }
        repaint();
    }

    void start()
    {
        if(paused)
        {
            paused = false;
            display.setCurrent(this);
        }
        repaint();
    }

    public void initTabla()
    {
        for(int x = 0; x < meret; x++)
        {
            for(int y = 0; y < meret; y++)
            {
                Tabla[x][y] = 0;
            }

        }

        int max = palya;
        for(int i = 1; i <= max; i++)
        {
            Tabla[(byte)veletlen(9) - 1][(byte)veletlen(9) - 1] = (byte)veletlen(maxGolyo);
        }

        if(Tabla[0][0] != 0 && Tabla[0][8] != 0 && Tabla[8][0] != 0 && Tabla[8][8] != 0)
        {
            switch(veletlen(4))
            {
            case 1: // '\001'
                Tabla[0][0] = 0;
                break;

            case 2: // '\002'
                Tabla[0][8] = 0;
                break;

            case 3: // '\003'
                Tabla[8][0] = 0;
                break;

            case 4: // '\004'
                Tabla[8][8] = 0;
                break;
            }
        }
    }

    public int veletlen(int szam)
    {
        return (random.nextInt() >>> 1) % szam + 1;
    }

    public void initTalon()
    {
        for(int i = 0; i <= 3; i++)
        {
            for(int y = 0; y < meret; y++)
            {
                Talon[i][y] = (byte)veletlen(maxGolyo);
            }

        }

    }

    public void drawTabla()
    {
        for(int x = 0; x < meret; x++)
        {
            for(int y = 0; y < meret; y++)
            {
                if(Tabla[x][y] != 0);
            }

        }

    }

    public void drawGolyoTabla(Graphics g, int xx, int yy, int c)
    {
        drawGolyoXY(g, xEltolas + xx * (r + Hezag), yEltolas + yy * (r + Hezag), c, r);
    }

    public void drawGolyoTabla(Graphics g, int xx, int yy, int c, int ujr)
    {
        drawGolyoXY(g, (xEltolas + xx * (r + Hezag) + r / 2) - ujr / 2, (yEltolas + yy * (r + Hezag) + r / 2) - ujr / 2, c, ujr);
    }

    public void drawGolyoXY(Graphics g, int xx, int yy, int c, int r)
    {
        xx++;
        yy++;
        if(display.isColor() && !FF)
        {
            switch(Mod)
            {
            case 1: // '\001'
                drawSzinesGolyoXY(g, xx, yy, szin[c], r);
                break;

            case 2: // '\002'
                drawTeltKorXY(g, xx, yy, szin[c] + 0x303030, r);
                drawTeltKorXY(g, xx, yy, szin[c] + 0x505050, r);
                break;

            case 3: // '\003'
                drawTeltKorXY(g, xx, yy, szin[c] + 0x505050, r);
                drawUresKorXY(g, xx, yy, szin[c], r);
                break;

            case 4: // '\004'
                switch(c)
                {
                case 1: // '\001'
                case 2: // '\002'
                    drawTeltKorXY(g, xx, yy, szin[c] + 0x505050, r);
                    drawUresKorXY(g, xx, yy, szin[c], r);
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    drawTeltNegyzetXY(g, xx, yy, szin[c] + 0x505050, r);
                    drawUresNegyzetXY(g, xx, yy, szin[c], r);
                    break;

                case 5: // '\005'
                case 6: // '\006'
                    drawTeltHaromszogXY(g, xx, yy, szin[c] + 0x505050, r);
                    drawUresHaromszogXY(g, xx, yy, szin[c], r);
                    break;

                case 7: // '\007'
                case 8: // '\b'
                    drawTeltHaromszog2XY(g, xx, yy, szin[c] + 0x505050, r);
                    drawUresHaromszog2XY(g, xx, yy, szin[c], r);
                    break;
                }
                break;
            }
        } else
        {
            drawAbraXY(g, xx, yy, c, 0, r);
        }
    }

    public void drawAbraXY(Graphics g, int xx, int yy, int tip, int c, int r)
    {
        switch(tip)
        {
        case 1: // '\001'
            drawTeltKorXY(g, xx, yy, c, r);
            break;

        case 2: // '\002'
            drawUresKorXY(g, xx, yy, c, r);
            break;

        case 3: // '\003'
            drawTeltNegyzetXY(g, xx, yy, c, r);
            break;

        case 4: // '\004'
            drawUresNegyzetXY(g, xx, yy, c, r);
            break;

        case 5: // '\005'
            drawTeltHaromszogXY(g, xx, yy, c, r);
            break;

        case 6: // '\006'
            drawUresHaromszogXY(g, xx, yy, c, r);
            break;

        case 7: // '\007'
            drawTeltHaromszog2XY(g, xx, yy, c, r);
            break;

        case 8: // '\b'
            drawUresHaromszog2XY(g, xx, yy, c, r);
            break;
        }
    }

    public void drawSzinesGolyoXY(Graphics g, int xx, int yy, int c, int r)
    {
        g.setColor(c + 0x303030);
        g.fillArc(xx, yy, r - Levonas, r - Levonas, 0, 360);
        g.setColor(c + 0x505050);
        g.fillArc(xx + r / 3, yy + (2 * r) / 5, r / 2, r / 2, 10, 280);
        g.setColor(c + 0x808080);
        g.fillArc(xx + r / 5, yy + r / 4, r / 3, r / 3, 100, -260);
    }

    public void drawTeltKorXY(Graphics g, int xx, int yy, int c, int r)
    {
        g.setColor(c);
        g.fillArc(xx, yy, r - Levonas, r - Levonas, 0, 360);
    }

    public void drawUresKorXY(Graphics g, int xx, int yy, int c, int r)
    {
        g.setColor(c);
        g.drawArc(xx, yy, r - Levonas, r - Levonas, 0, 360);
    }

    public void drawTeltNegyzetXY(Graphics g, int xx, int yy, int c, int r)
    {
        g.setColor(c);
        g.fillRect(xx, yy, r - Levonas - 1, r - Levonas - 1);
    }

    public void drawUresNegyzetXY(Graphics g, int xx, int yy, int c, int r)
    {
        g.setColor(c);
        g.drawRect(xx, yy, r - Levonas - 1, r - Levonas - 1);
    }

    public void drawTeltHaromszogXY(Graphics g, int xx, int yy, int c, int r)
    {
        if(r < 1)
        {
            return;
        }
        g.setColor(c);
        for(int i = 1; i < r; i++)
        {
            g.drawLine((xx + r / 2) - i / 2, (yy + i) - 1, xx + r / 2 + (i <= 1 ? 0 : i / 2 - 1), (yy + i) - 1);
        }

    }

    public void drawUresHaromszogXY(Graphics g, int xx, int yy, int c, int r)
    {
        if(r < 1)
        {
            return;
        } else
        {
            g.setColor(c);
            g.drawLine((xx + r) - Levonas - 1, (yy + r) - Levonas - 1, xx + r / 2, yy);
            g.drawLine((xx + r / 2) - (1 - r % 2), yy, xx, (yy + r) - Levonas - 1);
            g.drawLine(xx, (yy + r) - Levonas - 1, (xx + r) - Levonas - 1, (yy + r) - Levonas - 1);
            return;
        }
    }

    public void drawTeltHaromszog2XY(Graphics g, int xx, int yy, int c, int r)
    {
        if(r < 1)
        {
            return;
        }
        g.setColor(c);
        for(int i = 1; i < r; i++)
        {
            g.drawLine((xx + i) - 1, (yy + r / 2) - i / 2, (xx + i) - 1, yy + r / 2 + (i <= 1 ? 0 : i / 2 - 1));
        }

    }

    public void drawUresHaromszog2XY(Graphics g, int xx, int yy, int c, int r)
    {
        if(r < 1)
        {
            return;
        } else
        {
            g.setColor(c);
            g.drawLine((xx + r) - Levonas - 1, (yy + r) - Levonas - 1, xx, yy + r / 2);
            g.drawLine(xx, (yy + r / 2) - (1 - r % 2), (xx + r) - Levonas - 1, yy);
            g.drawLine((xx + r) - Levonas - 1, yy, (xx + r) - Levonas - 1, (yy + r) - Levonas - 1);
            return;
        }
    }

    public void drawKurzorKeret(Graphics g, int x, int y)
    {
        g.setColor(kurzorSzin);
        g.fillRect(x - Hezag / 2, y - Hezag / 2, r + Hezag, r + Hezag);
        g.setColor(0);
        g.drawRect(x - Hezag / 2, y - Hezag / 2, r + Hezag, r + Hezag);
    }

    public void Keres(int i, int j, byte g)
    {
        if(i < 8 && Tabla[i + 1][j] == g)
        {
            Tabla[i + 1][j] += 10;
            Keres(i + 1, j, g);
        }
        if(i > 0 && Tabla[i - 1][j] == g)
        {
            Tabla[i - 1][j] += 10;
            Keres(i - 1, j, g);
        }
        if(j < 8 && Tabla[i][j + 1] == g)
        {
            Tabla[i][j + 1] += 10;
            Keres(i, j + 1, g);
        }
        if(j > 0 && Tabla[i][j - 1] == g)
        {
            Tabla[i][j - 1] += 10;
            Keres(i, j - 1, g);
        }
    }

    public void Levesz(int x, int y, byte g)
    {
        Keres(x, y, g);
        rajz = 1;
        int db = 0;
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(Tabla[i][j] >= 10)
                {
                    db++;
                }
            }

        }

        if(db >= 2)
        {
            rajz = 6;
        }
    }

    public void Eltuntet(Graphics g, int ujr)
    {
        wait(20 / r);
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(Tabla[i][j] >= 10)
                {
                    updateGolyoLevesz(g, i, j, ujr);
                }
            }

        }

    }

    public void veglegEltuntet(Graphics g)
    {
        int db = 0;
        int db2 = 0;
        rajz = 0;
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(Tabla[i][j] >= 10)
                {
                    Tabla[i][j] = 0;
                    db2++;
                    Hatter(g, xEltolas + i * (r + Hezag) + 1, yEltolas + j * (r + Hezag) + 1, (r + Hezag) - Levonas, (r + Hezag) - Levonas);
                    Racs(g);
                }
                if(Tabla[i][j] != 0)
                {
                    db++;
                }
            }

        }

        if(db == 0)
        {
            rajz = 10;
            score += 100 + db2;
        } else
        {
            score += db2;
        }
        dbStr = " " + db;
    }

    public void wait(int msec)
    {
        try
        {
            Thread.sleep(msec);
        }
        catch(InterruptedException e) { }
    }

    public boolean moveto(Graphics g, int ie, int je, int i, int j, byte tip)
    {
        if(Tabla[i][j] == 0)
        {
            wait(1);
            Tabla[ie][je] = 0;
            Tabla[i][j] = tip;
            Hatter(g, xEltolas + ie * (r + Hezag) + 1, yEltolas + je * (r + Hezag) + 1, r - Levonas, r - Levonas);
            drawGolyoTabla(g, i, j, tip);
            return true;
        } else
        {
            return false;
        }
    }

    public void golyoLe(Graphics g, int melyik)
    {
        if(Tabla[melyik][0] == 0)
        {
            boolean vege = false;
            for(int k = 0; k < 9; k++)
            {
                if(Tabla[melyik][k] != 0)
                {
                    vege = true;
                }
            }

            if(vege)
            {
                int golyo = Talon[0][melyik];
                Talon[0][melyik] = (byte)veletlen(maxGolyo);
                Tabla[melyik][0] = (byte)golyo;
                drawKurzor(g);
                Golyok(g, melyik, 0, melyik, 0);
                rajz = 5;
                gx = melyik;
                gy = 0;
                gdx = 0;
                gdy = 1;
            }
        }
    }

    public void golyoFel(Graphics g, int melyik)
    {
        if(Tabla[melyik][8] == 0)
        {
            boolean vege = false;
            for(int k = 0; k < 9; k++)
            {
                if(Tabla[melyik][k] != 0)
                {
                    vege = true;
                }
            }

            if(vege)
            {
                int golyo = Talon[1][melyik];
                Talon[1][melyik] = (byte)veletlen(maxGolyo);
                Tabla[melyik][8] = (byte)golyo;
                drawKurzor(g);
                Golyok(g, melyik, 8, melyik, 8);
                rajz = 5;
                gx = melyik;
                gy = 8;
                gdx = 0;
                gdy = -1;
            }
        }
    }

    public void golyoJobbra(Graphics g, int melyik)
    {
        if(Tabla[0][melyik] == 0)
        {
            boolean vege = false;
            for(int k = 0; k < 9; k++)
            {
                if(Tabla[k][melyik] != 0)
                {
                    vege = true;
                }
            }

            if(vege)
            {
                int golyo = Talon[2][melyik];
                Talon[2][melyik] = (byte)veletlen(maxGolyo);
                Tabla[0][melyik] = (byte)golyo;
                drawKurzor(g);
                Golyok(g, 0, melyik, 0, melyik);
                rajz = 5;
                gx = 0;
                gy = melyik;
                gdx = 1;
                gdy = 0;
            }
        }
    }

    public void golyoBalra(Graphics g, int melyik)
    {
        if(Tabla[8][melyik] == 0)
        {
            boolean vege = false;
            for(int k = 0; k < 9; k++)
            {
                if(Tabla[k][melyik] != 0)
                {
                    vege = true;
                }
            }

            if(vege)
            {
                int golyo = Talon[3][melyik];
                Talon[3][melyik] = (byte)veletlen(maxGolyo);
                Tabla[8][melyik] = (byte)golyo;
                drawKurzor(g);
                Golyok(g, 8, melyik, 8, melyik);
                rajz = 5;
                gx = 8;
                gy = melyik;
                gdx = -1;
                gdy = 0;
            }
        }
    }

}
