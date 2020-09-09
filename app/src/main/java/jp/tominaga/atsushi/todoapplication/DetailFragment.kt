package jp.tominaga.atsushi.todoapplication

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_detail.*
import java.io.Serializable
import java.lang.RuntimeException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private val ARG_title = IntentKey.TITLE.name
private val ARG_deadline = IntentKey.DEADLINE.name
private val ARG_taskDetail = IntentKey.TASK_DETAIL.name
private val ARG_isCompleted = IntentKey.IS_COMPLETED.name




/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private var title: String? = ""
    private var deadline : String? = ""
    private var taskDetail : String? = ""
    private var isCompleted : Boolean = false

    private var mListener: OnFragmentInteractionListener? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_title)
            deadline = it.getString(ARG_deadline)
            taskDetail = it.getString(ARG_taskDetail)
            isCompleted = it.getBoolean(ARG_isCompleted)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        title_detail.text = title
        deadline_detail.text = deadline
        todo_detail.text = taskDetail
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.apply {
            findItem(R.id.menu_delete).isVisible = true
            findItem(R.id.menu_edit).isVisible = true
            findItem(R.id.menu_register).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.menu_delete ->{
                deleteSelectedTodo(title,deadline,taskDetail)

            }

            R.id.menu_edit ->{
                mListener?.onEditSelectedTodo(title!!, deadline!!, taskDetail!!, isCompleted, ModeInEdit.EDIT)

            }

        }
        return super.onOptionsItemSelected(item)

    }

    private fun deleteSelectedTodo(title: String?, deadline: String?, taskDetail: String?) {
        val realm = Realm.getDefaultInstance()
        val selectedTodo = realm.where(TodoModel::class.java)
            .equalTo(TodoModel::title.name, title)
            .equalTo(TodoModel::deadline.name, deadline)
            .equalTo(TodoModel::taskDetail.name, taskDetail)
            .findFirst()
        realm.beginTransaction()
        selectedTodo!!.deleteFromRealm()
        realm.commitTransaction()

        mListener?.onDataDeleted()
        requireFragmentManager().beginTransaction().remove(this).commit()

        realm.close()





    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnFragmentInteractionListener){
            mListener = context
        }else{
            throw RuntimeException(context?.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onDataDeleted()
        fun onEditSelectedTodo(title: String, deadline: String,taskDetail: String, isCompleted : Boolean, mode: ModeInEdit)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(title: String?, deadline: String?, taskDetail: String?, isCompleted: Boolean) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_title,title)
                    putString(ARG_deadline,deadline)
                    putString(ARG_taskDetail,taskDetail)
                    putBoolean(ARG_isCompleted,isCompleted)


                }
            }
    }
}
