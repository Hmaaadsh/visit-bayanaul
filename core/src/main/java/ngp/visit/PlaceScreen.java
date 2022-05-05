package ngp.visit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class PlaceScreen extends NgpScreen {
    private final LocationData locData;
    private TextButton title, aboutBn;
    private final Group imageStrip = new Group();
    private final NgpActor backdrop;
    private Button backBn, navBn;
    private final Array<Image> images;
    private Label text;
    private final boolean ready;

    public PlaceScreen(TourApp app, LocationData locData){
        super(app);
        this.locData = locData;
        navBn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.net.openURI(locData.url);
                event.handle();
            }
        });
        backdrop = new NgpActor(Color.DARK_GRAY,8,8);
        imageStrip.addActor(backdrop);
        images = new Array<>();
        for (int i = 0; i < 19; i++){
            Image img = null;
            String test = locData.imageLoc.concat(Integer.toString(i+1).concat(".png"));
            try{img = new Image(new Texture(test));}catch(Exception e){
                test = locData.imageLoc.concat(Integer.toString(i+1).concat(".jpg"));
                try{img = new Image(new Texture(test));}catch(Exception f){
                    test = locData.imageLoc.concat(Integer.toString(i+1).concat(".jpeg"));
                    try{img = new Image(new Texture(test));}catch(Exception ignored){}}}
            if (null!=img) {images.add(img);
                imageStrip.addActor(images.get(i));
                images.get(i).setPosition(i*850+50,0);}
        }
        stage.addActor(imageStrip);
        ready = true;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void refreshText(){
        title.setText(locData.name.get(app.language));
        text.setText(locData.contents.get(app.language));
        aboutBn.setText(Text.about_button.get(app.language));
    }

    public void initUI() {
        title = new TextButton("", Style.styleTextLarge);
        aboutBn = new TextButton(Text.about_button.get(app.language), Style.styleTextLarge);
        aboutBn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                app.setScreen(new AboutScreen(app));
            }
        });
        text = new Label("", Style.styleLabelLarge);
        text.setWrap(true);
        text.setAlignment(Align.topLeft);
        backBn = new Button(new TextureRegionDrawable(new Texture("images/ui/return.png")));
        backBn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new MainScreen(app));
            }
        });
        navBn = new Button(new TextureRegionDrawable(new Texture("images/ui/gmap.png")));
        stage.addActor(aboutBn);
        stage.addActor(title);
        stage.addActor(text);
        stage.addActor(backBn);
        stage.addActor(navBn);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {
        title.setBounds(0.2f*width,height-160,0.6f*width,128);
        text.setBounds(0.1f*width,200,0.8f*width,600);
        backBn.setBounds(36,height-132,96,96);
        navBn.setBounds(width-132,height-132,96,96);
        if (ready) {
            imageStrip.setBounds(0, height - 800, width, 600);
            backdrop.setBounds(0, height - 892, width, 700);
            for (int i = 0; i < images.size; i++) {
                images.get(i).setPosition(i * 850 + 50, 0);
            }
        }
        languages.setPosition(448, 32);
        aboutBn.setBounds(64, 32, 320,148);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }
}
