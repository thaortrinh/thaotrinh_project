package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage i1, i2, i3, i4, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;
    private BufferedImage[] idleAni, runAni;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private Boolean moving = false;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimations();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }
    private void importImg() {
        try {
            // IDLE
            i1 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - Idle1.png"));
            i2 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - Idle2.png"));
            i3 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - Idle3.png"));
            i4 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - Idle4.png"));

            // RUN
            r1 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - run1.png"));
            r2 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - run2.png"));
            r3 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - run3.png"));
            r4 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - run4.png"));
            r5 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - run5.png"));
            r6 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - run6.png"));
            r7 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - run7.png"));
            r8 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - run8.png"));
            r9 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - run9.png"));
            r10 = ImageIO.read(getClass().getResourceAsStream("/player/Hobbit - run10.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadAnimations() {
        idleAni = new BufferedImage[4];
        runAni = new BufferedImage[10];

        idleAni[0] = i1;
        idleAni[1] = i2;
        idleAni[2] = i3;
        idleAni[3] = i4;

        runAni[0] = r1;
        runAni[1] = r2;
        runAni[2] = r3;
        runAni[3] = r4;
        runAni[4] = r5;
        runAni[5] = r6;
        runAni[6] = r7;
        runAni[7] = r8;
        runAni[8] = r9;
        runAni[9] = r10;
    }
    public void setDirection(int direction) {
        this.playerDirection = direction;
        moving = true;
    }

    public void setMoving(Boolean moving) {
        this.moving = moving;
    }

    private void setAnimation() {
        if(moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }
    private void updatePos() {
        if(moving) {
            switch(playerDirection) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(960, 640);
        setPreferredSize(size);
    }

    public BufferedImage[] returnAniArray() {
        switch (playerAction) {
            case RUNNING:
                return runAni;
            default:
                return idleAni;
        }
    }
    private void updateAniTick() {
        aniTick++;

        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetAniAmount(playerAction)) {
                aniIndex = 0;
            }
        }

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateAniTick();
        setAnimation();
        updatePos();

        g.drawImage(returnAniArray()[aniIndex], (int) xDelta, (int) yDelta, 224, 224, null);

    }

}