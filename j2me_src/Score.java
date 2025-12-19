package gluglu;

import java.io.PrintStream;
import javax.microedition.rms.*;

class Score
{

    private int levelId;
    private byte levelRec[];
    private static final int LEVEL_LEN = 9;
    private static final byte LEVEL_TAG = 1;
    private int scoreId;
    private byte scoreRec[];
    private static final int SCORE_LEN = 13;
    private static final byte SCORE_TAG = 2;
    private RecordStore store;

    Score()
    {
        store = null;
        levelId = 0;
        levelRec = new byte[9];
        levelRec[0] = 1;
        putInt(levelRec, 1, 0);
        putInt(levelRec, 5, 0);
        scoreId = 0;
        scoreRec = new byte[13];
        scoreRec[0] = 2;
        putInt(scoreRec, 1, 0);
    }

    boolean open()
    {
label0:
        {
            System.out.println("open kezd\u0151dik");
            try
            {
                store = RecordStore.openRecordStore("GlugluScore", true);
            }
            catch(RecordStoreException ex)
            {
                ex.printStackTrace();
                System.out.println("ez baj");
            }
            System.out.println("open tov\341bb1");
            if(store == null)
            {
                return false;
            }
            System.out.println("open tov\341bb2");
            try
            {
                levelId = 0;
                RecordEnumeration enum = store.enumerateRecords(null, null, false);
                int ndx;
                int l;
                do
                {
                    do
                    {
                        if(!enum.hasNextElement())
                        {
                            break label0;
                        }
                        ndx = enum.nextRecordId();
                    } while(store.getRecordSize(ndx) != 9);
                    l = store.getRecord(ndx, levelRec, 0);
                } while(l != 9 || levelRec[0] != 1);
                levelId = ndx;
            }
            catch(RecordStoreException ex)
            {
                ex.printStackTrace();
                return false;
            }
        }
        System.out.println("open v\351gz\u0151dik Level" + getInt(levelRec, 1) + " lang" + getInt(levelRec, 5));
        return true;
    }

    int getTheme()
    {
        return getInt(levelRec, 5);
    }

    int getLevel()
    {
        return getInt(levelRec, 1);
    }

    boolean setLevel(int level, int theme)
    {
        putInt(levelRec, 1, level);
        putInt(levelRec, 5, theme);
        putInt(scoreRec, 1, level);
        System.out.println("Level out:" + level + " Lang out" + theme);
        if(store == null)
        {
            return false;
        }
        try
        {
            if(levelId == 0)
            {
                levelId = store.addRecord(levelRec, 0, levelRec.length);
            } else
            {
                store.setRecord(levelId, levelRec, 0, levelRec.length);
            }
        }
        catch(RecordStoreException ex)
        {
            System.out.println("RecordStoreException");
            ex.printStackTrace();
            return false;
        }
        readScore(level);
        return true;
    }

    int getPushes()
    {
        return getInt(scoreRec, 5);
    }

    int getMoves()
    {
        return getInt(scoreRec, 9);
    }

    boolean readScore(int level)
    {
        RecordEnumeration enum;
        scoreId = 0;
        enum = store.enumerateRecords(null, null, false);
        int ndx;
        int l;
        do
        {
            do
            {
                if(!enum.hasNextElement())
                {
                    break MISSING_BLOCK_LABEL_110;
                }
                ndx = enum.nextRecordId();
            } while(store.getRecordSize(ndx) != 13);
            l = store.getRecord(ndx, scoreRec, 0);
        } while(l != 13 || scoreRec[0] != 2 || getInt(scoreRec, 1) != level);
        scoreId = ndx;
        return true;
        RecordStoreException ex;
        ex;
        ex.printStackTrace();
        return false;
        scoreRec[0] = 2;
        putInt(scoreRec, 1, level);
        putInt(scoreRec, 5, 0);
        putInt(scoreRec, 9, 0);
        return true;
    }

    boolean setLevelScore(int pushes, int moves)
    {
        putInt(scoreRec, 5, pushes);
        putInt(scoreRec, 9, moves);
        try
        {
            if(scoreId == 0)
            {
                scoreId = store.addRecord(scoreRec, 0, scoreRec.length);
            } else
            {
                store.setRecord(scoreId, scoreRec, 0, scoreRec.length);
            }
        }
        catch(RecordStoreException ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private int getInt(byte buf[], int offset)
    {
        return (buf[offset + 0] & 0xff) << 24 | (buf[offset + 1] & 0xff) << 16 | (buf[offset + 2] & 0xff) << 8 | buf[offset + 3] & 0xff;
    }

    private void putInt(byte buf[], int offset, int value)
    {
        buf[offset + 0] = (byte)(value >> 24 & 0xff);
        buf[offset + 1] = (byte)(value >> 16 & 0xff);
        buf[offset + 2] = (byte)(value >> 8 & 0xff);
        buf[offset + 3] = (byte)(value >> 0 & 0xff);
    }

    void close()
    {
        try
        {
            if(store != null)
            {
                store.closeRecordStore();
            }
        }
        catch(RecordStoreException ex)
        {
            ex.printStackTrace();
        }
    }
}
