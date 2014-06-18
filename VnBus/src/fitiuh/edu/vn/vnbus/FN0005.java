package fitiuh.edu.vn.vnbus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FN0005 extends Activity {
	
	ImageView imgFavorite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fn0005);
		
		imgFavorite = (ImageView)findViewById(R.id.imageView1);
	    imgFavorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				open();
			}
	      });
	}
	
	public void open(){
	      Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	      startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
	      super.onActivityResult(requestCode, resultCode, data);
	      Bitmap bp = (Bitmap) data.getExtras().get("data");
	      imgFavorite.setImageBitmap(bp);
	}
}
