/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.controlroomapp;

/**
 *
 * @author Riad
 */
public enum VASServices {

    JAZEERA {
                public String toString() {
                    return "Al-Jazeera";
                }
            },
    BOY_TIPS {
                public String toString() {
                    return "Boy Tips";
                }
            },
    GIRL_TIPS {
                public String toString() {
                    return "Girl Tips";
                }
            },
    CURRENCY {
                public String toString() {
                    return "Currency Exchange";
                }
            },
    HOROSCOPE {
                public String toString() {
                    return "Horoscope";
                }
            },
    PRAYER {
                public String toString() {
                    return "Prayer Times";
                }
            },
    SPORTS_NEWS {
                public String toString() {
                    return "Sports News";
                }
            },
    FACEBOOK {
                public String toString() {
                    return "Facebook";
                }
            },
    CRBT {
                public String toString() {
                    return "Fun Ring";
                }
            },
    SMS {
                public String toString() {
                    return "SMS Express";
                }
            },
    ALLAH_NAMES {
                public String toString() {
                    return "99 Names of Allah";
                }
            },
    KOLAREH {
                @Override
                public String toString() {
                    return "Kolareh";
                }
            };
}
