package themes;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class MyTheme extends FlatMacLightLaf {

        public static boolean setup() {
            return setup( new MyTheme() );
        }

        @Override
        public String getName() {
            return "MyTheme";
        }
    }

