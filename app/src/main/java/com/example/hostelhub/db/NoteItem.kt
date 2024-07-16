package com.example.hostelhub.db

data class NoteItem(var Notice:String,val desc:String,val noteId:String) {
    constructor() : this("", "", "")
}
