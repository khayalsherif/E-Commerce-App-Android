package az.red.presentation.content.orders.dialog

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.DialogFragment
import az.red.domain.model.cart.CartProduct
import az.red.domain.model.review.request.AddCommentRequest
import az.red.presentation.R
import az.red.presentation.databinding.LayoutLeaveReviewBinding
import coil.load
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeaveReviewDialog(private val cartProduct: CartProduct) : DialogFragment() {
    private lateinit var binding : LayoutLeaveReviewBinding
    private val pickImage = 100
    private var imageUri: Uri? = null
    val leaveReviewViewModel: LeaveReviewViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutLeaveReviewBinding.inflate(inflater)

        if(dialog != null && dialog!!.window != null){
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
//            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvDialogProductName.text = cartProduct.product.name
            tvDialogProductPrice.text = "US $${cartProduct.product.currentPrice}"
            imageDialog.load(cartProduct.product.imageUrls.first())

            llAddPhoto.setOnClickListener {

            }

            btnCancel.setOnClickListener {
                dialog!!.dismiss()
            }

            btnSubmit.setOnClickListener {
                val addCommentRequest = AddCommentRequest(cartProduct.product._id,inputTypeReview.text.toString())
                leaveReviewViewModel.addComment(addCommentRequest)
                Snackbar.make(requireView(),getString(R.string.comment_added_successfully),Snackbar.LENGTH_SHORT).show()
                Handler().postDelayed({
                    dialog!!.dismiss()
                },3000L)

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data

        }
    }

    override fun onStart() {
        if(dialog != null && dialog!!.window != null){
            dialog!!.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        super.onStart()
    }

}