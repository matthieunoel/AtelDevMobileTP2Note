package com.epsi.mnoel


class Livre {

    var id: Int? = null
    var titre: String? = null
    var desc: String? = null
    var auteur: String? = null
    var img: String? = null

    constructor() {
        this.id = -1
    }

    constructor(id: Int?, titre: String?, desc: String?) : this() {
        this.id = id
        this.titre = titre
        this.desc = desc
    }

    constructor(id: Int?, titre: String?, desc: String?, auteur: String?, img: String?) : this() {
        this.id = id
        this.titre = titre
        this.desc = desc
        this.auteur = auteur
        this.img = img
    }

}