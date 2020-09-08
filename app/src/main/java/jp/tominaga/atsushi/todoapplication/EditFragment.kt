package jp.tominaga.atsushi.todoapplication

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_edit.*
import java.lang.RuntimeException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private val ARG_title = IntentKey.TITLE.name
private val ARG_deadline = IntentKey.DEADLINE.name
private val ARG_taskDetail = IntentKey.TASK_DETAIL.name
private val ARG_isCompleted = IntentKey.IS_COMPLETED.name
private val ARG_mode = IntentKey.MODE_IN_EDIT.name

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var title: String? = ""
    private var deadline : String? = ""
    private var taskDetail : String? = ""
    private var isCompleted : Boolean = false
    private var mode : ModeInEdit? = null

    private var mListener: OnFragmentInteractionListener? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_title)
            deadline = it.getString(ARG_deadline)
            taskDetail = it.getString(ARG_taskDetail)
            isCompleted = it.getBoolean(ARG_isCompleted)
            mode = it.getSerializable(ARG_mode) as ModeInEdit



        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("EditMode","EditFragment#onActivityCreared: " + mode.toString())
        updateUi(mode!!)

    }

    private fun updateUi(mode : ModeInEdit) {
        //画面の更新処理
        when(mode){
            ModeInEdit.NEW_ENTRY -> {
                checkBox.visibility = View.INVISIBLE
            }

            ModeInEdit.EDIT -> {

            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.apply {
            findItem(R.id.menu_delete).isVisible = false
            findItem(R.id.menu_edit).isVisible = false
            findItem(R.id.menu_register).isVisible = true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener){
            mListener = context
        } else {
            throw RuntimeException(context.toString() + "must impement OnFramentIteractionLstner")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Todo DBへの登録処理

        return super.onOptionsItemSelected(item)
    }

    interface OnFragmentInteractionListener{
        fun onFragmentInteaction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(title: String, deadline: String, taskDetail: String, isCompleted: Boolean,mode: ModeInEdit) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_title,title)
                    putString(ARG_deadline,deadline)
                    putString(ARG_taskDetail,taskDetail)
                    putBoolean(ARG_isCompleted,isCompleted)
                    putSerializable(ARG_mode,mode)

                }
            }
    }
}
