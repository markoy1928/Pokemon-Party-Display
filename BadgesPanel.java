import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import java.io.File;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BadgesPanel extends JPanel {
    private static int DIM_WIDTH;
    private static int DIM_HEIGHT;
    private JFrame frame;
    private Color backgroundColor;
    private float scale;
    private static Game game;
    private BadgePanel[] badgePanels;
    private boolean[] badges;

    public void createBadgeGUI(Game game, boolean[] badges, Color bgColor, float scale) {
        this.badges = badges;
        BadgesPanel.game = game;
        DIM_WIDTH = Math.round(250 * scale);
        DIM_HEIGHT = Math.round(getFrameHeight() * scale);
        badgePanels = new BadgePanel[getBadgesLength()];
        frame = new JFrame("Badges");
        ImageIcon frameIcon = new ImageIcon("Pokemon/Dragapult.png");
        frame.setIconImage(frameIcon.getImage());
        this.backgroundColor = bgColor;
        this.scale = scale;

        // Add each badge to the panel
        for (int i = 0; i < getBadgesLength(); ++i) {
            BadgePanel badge = new BadgePanel(i + 1);
            badgePanels[i] = badge;
            this.add(badge, i);
        }

        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private static int getFrameHeight() {
        if (game == Game.GSC || game == Game.HGSS) {
            return 850;
        }
        else {
            return 425;
        }
    }

    private int getBadgesLength() {
        if (game == Game.GSC || game == Game.HGSS) {
            return 16;
        }
        else {
            return 8;
        }
    }

    public void refreshPanel(boolean[] badges) {
        frame.getContentPane().removeAll();
        this.badges = badges;

        for (int i = 0; i < getBadgesLength(); ++i) {
            this.remove(i);
            BadgePanel badge = new BadgePanel(i + 1);
            this.add(badge, i);
        }

        frame.add(this);
        this.revalidate();
        this.repaint();
    }

    public Dimension getPreferredSize() {
        return new Dimension(DIM_WIDTH, DIM_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        setBackground(backgroundColor);
        super.paintComponent(g);
    }

    private class BadgePanel extends JPanel {
        private int gymNumber;

        public BadgePanel(int gymNumber) {
            this.gymNumber = gymNumber;
            this.setPreferredSize(new Dimension(Math.round(95 * scale), Math.round(95 * scale)));
            this.setBackground(backgroundColor);

            try {
                File f = new File(getFilePath());
                URL imgUrl = f.toURI().toURL();
                Icon icon = new ImageIcon(imgUrl);
                ((ImageIcon) icon).setImage(((ImageIcon) icon).getImage().getScaledInstance(Math.round(80 * scale), Math.round(80 * scale), Image.SCALE_FAST));

                JLabel bLabel = new JLabel(icon);
                this.add(bLabel);
            }
            catch (Exception e) {
                System.err.println("ERROR: " + e.getMessage());
                System.err.println(e.getStackTrace()[0].getLineNumber());
                System.err.println(e.getStackTrace()[0].getFileName());
            }
        }

        private String getFilePath() {
            PokeDB db = new PokeDB();
            String badgeName;
            
            if (game.getRegion() == "Johto" && gymNumber > 8) {
                badgeName = db.select("Gyms", "badge_name", "region = 'Kanto' and gym_number = " + (gymNumber - 8)).get(0);
            }
            else {
                badgeName = db.select("Gyms", "badge_name", String.format("region = '%s' and gym_number = %d", game.getRegion(), gymNumber)).get(0);
            }

            // Determine if the badge has been received yet
            if (badges[gymNumber - 1]) {
                return "Badges/" + badgeName.toLowerCase() + ".png";
            }
            else {
                return "Badges/" + badgeName.toLowerCase() + "-empty.png";
            }
        }
    }
}
