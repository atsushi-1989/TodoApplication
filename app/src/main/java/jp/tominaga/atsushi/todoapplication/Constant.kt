package jp.tominaga.atsushi.todoapplication

enum class IntentKey{
    TITLE, DEADLINE, TASK_DETAIL, IS_COMPLETED, MODE_IN_EDIT
}

enum class ModeInEdit {
    NEW_ENTRY, EDIT
}

enum class FragmentTag {
    MASTER, DETAIL, EDIT
}