package com.example.myapplication.owner

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_owner.*
import kotlinx.android.synthetic.main.bottomsheet_images.*
import kotlinx.android.synthetic.main.bottomsheet_images.view.*
import kotlinx.android.synthetic.main.dialog_image_selector.view.*
import kotlinx.android.synthetic.main.total_shop_bottomsheet.view.*
import java.io.File


class OwnerActivity: AppCompatActivity(), View.OnClickListener {

    val arrShop= arrayOf("Chanel", "Central Perk", "Walmart")
    val songList= arrayListOf<String>()
//    val songList= arrayOf("Umbrella","Our song","You")

    private lateinit var mUri: Uri

    var images = arrayListOf(
            R.drawable.shop1, R.drawable.walmart,
            R.drawable.chanel, R.drawable.shop,
            R.drawable.ic_add, R.drawable.ic_add_circle,
            R.drawable.ic_more_horiz
    )

    lateinit var bottomSheetDialog:BottomSheetDialog
    lateinit var imageSheetDialog: BottomSheetDialog

    lateinit var rvBottomOptionList:RecyclerView
    lateinit var rvImageBottomSheet:RecyclerView

    lateinit var tvBottomSheet:TextView
    lateinit var totalShopAdapter:TotalShopAdapter
    lateinit var liveShopAdapter:LiveShopAdapter
    lateinit var imageAdapter:ImageAdapter
    private var imageUri: Uri? = null

    lateinit var musicAdapter:MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)

        initView()
    }

    private fun initView() {
        songList.add("Umbrella")
        songList.add("You")
        songList.add("Our song")
        musicAdapter=MusicAdapter(this, songList)
        rvMusic.adapter=musicAdapter

        val totalShop=SpannableString(tvTotalShop.text.toString())
        totalShop.setSpan(
                AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
                totalShop.length - 2,
                totalShop.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvTotalShop.text=totalShop

        val liveShop=SpannableString(tvLiveShop.text.toString())
        liveShop.setSpan(
                AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
                liveShop.length - 2,
                liveShop.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvLiveShop.text=liveShop

        val totalImages=SpannableString(tvTotalImages.text.toString())
        totalImages.setSpan(
                AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
                totalImages.length - 2,
                totalImages.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvTotalImages.text=totalImages

        val music=SpannableString(tvMusic.text.toString())
        music.setSpan(
                AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
                music.length - 2,
                music.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvMusic.text=music

        bottomSheetDialog=BottomSheetDialog(this)
        imageSheetDialog= BottomSheetDialog(this)

        val totalShopSheet= layoutInflater.inflate(R.layout.total_shop_bottomsheet, null)
        bottomSheetDialog.setContentView(totalShopSheet)

        val totalImageSheet= layoutInflater.inflate(R.layout.bottomsheet_images, null)
        imageSheetDialog.setContentView(totalImageSheet)

        totalShopAdapter=TotalShopAdapter(this)
        rvBottomOptionList = totalShopSheet.rvListShop
        tvBottomSheet=totalShopSheet.tvHeading

        liveShopAdapter= LiveShopAdapter(this)

        imageAdapter= ImageAdapter(this, images)
        totalImageSheet.rvImageList.setLayoutManager(GridLayoutManager(this, 2))
        rvImageBottomSheet = totalImageSheet.rvImageList

    }

    fun dispatchTakePictureIntent(cameraCode:Int){

        val values= ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION,"from the gallery")

        Log.e("TakePictureIntent function","checking")
        mUri= this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)!!

        Log.e("1","checking")
        val takePictureIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.setAction(Intent.ACTION_GET_CONTENT)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,mUri)
        startActivityForResult(takePictureIntent,cameraCode)

    }

    fun openGallery(galleryCode:Int){
        Log.e("openGallery Function","checking")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,galleryCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode==Activity.RESULT_OK && requestCode==11){
            ivOwnerProfile.setImageURI(data?.data)
            Log.e("PAth:", data?.data.toString())
            //file=File(data.toString())
            mUri= data?.data!!
        }
        if (requestCode==12 && resultCode== Activity.RESULT_OK ){
            ivOwnerProfile.setImageURI(mUri)
            Log.e("path:",mUri.toString())
            //file= File(mUri.toString())
            mUri= Uri.parse(data?.data.toString())
        }

        if (resultCode==Activity.RESULT_OK && requestCode==21){
            ivOwnerProfile.setImageURI(data?.data)
//            images.add(data?.data)
            Log.e("PAth:", data?.data.toString())
            //file=File(data.toString())
            mUri= data?.data!!
        }

        if (requestCode==22 && resultCode== Activity.RESULT_OK ){
            ivOwnerProfile.setImageURI(mUri)
            Log.e("path:",mUri.toString())
            //file= File(mUri.toString())
            mUri= Uri.parse(data?.data.toString())
        }

        if (requestCode==3 && resultCode==Activity.RESULT_OK){
            if ((data!=null)&&(data.getData()!=null)){
                var audioFileUri:Uri= data.getData()!!

                val fileName = File(audioFileUri.path).name
                songList.add(fileName)
                Log.e("file name", "onActivityResult: " + fileName)

                musicAdapter=MusicAdapter(this, songList)
                rvMusic.adapter=musicAdapter
            }
        }
    }


    override fun onClick(p0: View?) {
        if (p0 != null) {
            when(p0.id){
                R.id.tvTotalShop -> {
                    Log.e("OwnerActivity", "onClick")
                    tvBottomSheet.text = "Total Shops"
                    rvBottomOptionList.adapter = totalShopAdapter
                    bottomSheetDialog.show()
                }
                R.id.tvLiveShop -> {
                    bottomSheetDialog.show()
                    tvBottomSheet.text = "Live Shops"
                    rvBottomOptionList.adapter = liveShopAdapter
                    bottomSheetDialog.show()
                }

                R.id.tvTotalImages -> {
                    if (rvImageList != null) {
                        rvImageList.setLayoutManager(GridLayoutManager(this, 2))
                    }

                    rvImageBottomSheet.adapter = imageAdapter
                    imageSheetDialog.show()

                    imageSheetDialog.ivAddImage.setOnClickListener {
                        Log.e("Bottomsheet Image Add","Checking")
                        image(2)
                    }
                }

                R.id.ivAdd -> {
                    val intent: Intent = Intent()
                    intent.setAction(Intent.ACTION_GET_CONTENT)
                    intent.setType("audio/*")
                    startActivityForResult(Intent.createChooser(intent, "Select Music"), 3)
                    Log.e("Add music", "checking")
                }
                R.id.ivOwnerProfile -> {
                    Log.e("Image on-click", "Checking")

                    image(1)

//                    val dialogView = layoutInflater.inflate(R.layout.dialog_image_selector, null)
//                    val builder = AlertDialog.Builder(this)
//                            .setView(dialogView)
//                    val alertDialog = builder.show()
//
//                    dialogView.tvCamera.setOnClickListener {
//                        alertDialog.hide()
//                        Log.e("tvCamera onclick","checking")
//
//                        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
//                            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
//                                    PackageManager.PERMISSION_DENIED && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
//                                    PackageManager.PERMISSION_DENIED ){
//                                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                requestPermissions(permissions, 1001)
//                            }
//                            if (checkSelfPermission(android.Manifest.permission.CAMERA)==
//                                    PackageManager.PERMISSION_DENIED){
//                                val permission = arrayOf(android.Manifest.permission.CAMERA)
//                                requestPermissions(permission,1000)
//                            }
//                            else{
//                                dispatchTakePictureIntent()
//                            }
//                        }
//                        else{
//                            dispatchTakePictureIntent()
//                        }
//                    }
//
//                    dialogView.tvGallery.setOnClickListener {
//                        alertDialog.hide()
//
//                        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//                            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)==
//                                    PackageManager.PERMISSION_DENIED){
//                                val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                                requestPermissions(permission,1001)
//                            }
//                            else{
//                                openGallery()
//                            }
//                        }
//                        else{
//                            openGallery()
//                        }
//                    }
//
//                    dialogView.tvCancel.setOnClickListener {
//                        alertDialog.dismiss()
//                    }
                }
            }
        }
    }

    private fun image(code:Int) {
        var cameraCode:Int=0
        var galleryCode:Int=0
        val dialogView = layoutInflater.inflate(R.layout.dialog_image_selector, null)
        val builder = AlertDialog.Builder(this)
                .setView(dialogView)
        val alertDialog = builder.show()
        if (code==1){
            cameraCode=11
            galleryCode=12
        }
        else if (code==2){
            cameraCode=21
            galleryCode=22
        }

        dialogView.tvCamera.setOnClickListener {
            alertDialog.hide()
            Log.e("tvCamera onclick","checking")

            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED ){
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, 1001)
                }
                if (checkSelfPermission(android.Manifest.permission.CAMERA)==
                        PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(android.Manifest.permission.CAMERA)
                    requestPermissions(permission,1000)
                }
                else {
                    dispatchTakePictureIntent(cameraCode)
                }
            }
            else{
                dispatchTakePictureIntent(cameraCode)
            }
        }

        dialogView.tvGallery.setOnClickListener {
            alertDialog.hide()

            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)==
                        PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission,1001)
                }
                else{
                    openGallery(galleryCode)
                }
            }
            else{
                openGallery(galleryCode)
            }
        }

        dialogView.tvCancel.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}