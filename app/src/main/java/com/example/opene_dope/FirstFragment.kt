package com.example.opene_dope

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.opene_dope.databinding.FragmentFirstBinding
import androidx.compose.ui.platform.LocalContext

class FirstFragment() : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.composeView.setContent {
            MaterialTheme { // Use MaterialTheme directly
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainActivity = requireActivity() as MainActivity
                    val myBitmap = mainActivity.createTableImage(mainActivity.tableData)
                    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

                    LaunchedEffect(myBitmap) {
                        imageBitmap = try {
                            myBitmap.asImageBitmap(LocalContext.current.resources) // Fix: Use LocalContext.current.resources
                        } catch (e: Exception) {
                            null
                        }
                    }
                }

                MyScreen(imageBitmap)
            }

            return binding.root
        }
        fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }

        @Composable
        fun MyScreen(tableBitmap: ImageBitmap?) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (tableBitmap != null) {
                    Image(
                        bitmap = tableBitmap,
                        contentDescription = "Table Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) // Adjust height as needed
                    )
                } else {
                    CircularProgressIndicator()
                }
            }
        }

        fun Bitmap.asImageBitmap(resources: android.content.res.Resources): ImageBitmap {
            val source = ImageDecoder.createSource(resources, this)
            return ImageDecoder.decodeBitmap(source).asImageBitmap()
        }
    }
