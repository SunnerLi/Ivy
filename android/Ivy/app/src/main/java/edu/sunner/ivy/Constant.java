package edu.sunner.ivy;

/**
 * The Constants class
 *
 * @author sunner
 * @since 9/3/16.
 */
public class Constant {
    // Bundle Key
    public static final String MODE_KEY = "mode key";

    // Shared Preference Name
    public static final String PRE_NAME = "Preference Ivy name";

    // Preference Key
    public static final String SETTING_SHOWTEXT_KEY = "show key";
    public static final String SETTING_SPEAKING_KEY = "speak key";
    public static final String SETTING_MODE_KEY = "mode key";

    // Mode Constant
    public static final int FUNDAMENTAL = 0;
    public static final int ADVANCE = 1;
    public static final int STRENGTHEN = 2;
    public static final int LISTEN = 3;

    // Date limitation
    public static final int YEAR_MAX = 2016;
    public static final int MONTH_MAX = 12;
    public static final int DAY_MAX = 31;
    public static final int HOUR_MAX = 24;
    public static final int MINUTE_MAX = 60;

    // Edition Constants
    public static final int VIVIAN = 100;
    public static final int IVY = 101;

    // Setting Constants
    public static final int YES = 1;
    public static final int NO = 0;
    public static final int FOUR_CHOOSE_ONE = 2;
    public static final int FOUR_DELETE_THREE = 3;

    /*
        Log tag
        The prefix rule: each capital letter and the final alphabet
     */
    public static final String DAY_TAG = "-->> DirectionActivity ";
    public static final String MAY_TAG = "-->> MainActivity ";
    public static final String ADFT_TAG = "-->> AdvanceDirectFragment ";
    public static final String FDFT_TAG = "-->> FundamentalDirectFragment ";
    public static final String LDFT_TAG = "-->> ListenDirectFragment ";
    public static final String SDFT_TAG = "-->> StrengthenDirectFragment ";
    public static final String CFFFT_TAG = "-->> ChooseFrom4 ";
    public static final String LFT_TAG = "-->> ListenFragment ";
    public static final String MFT_TAG = "-->> MainFragment ";
    public static final String SFT_TAG = "-->> SettingFragment ";
    public static final String DAR_TAG = "-->> DirectionAdapter ";
    public static final String LLAR_TAG = "-->> ListenListAdapter ";
    public static final String MLAR_TAG = "-->> MainListAdapter ";
    public static final String SLAR_TAG = "-->> SettingList ";
    public static final String CT_TAG = "-->> Constant ";
    public static final String DE_TAG = "-->> Date ";
    public static final String FS_TAG = "-->> FragmentStack ";
    public static final String PR_TAG = "-->> Parser ";
    public static final String WD_TAG = "-->> Word ";
}
