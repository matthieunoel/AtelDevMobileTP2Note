package com.epsi.mnoel

import android.os.Parcel
import android.os.Parcelable

public class Livre /*: Parcelable*/ {
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

    /*constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        titre = parcel.readString()
        desc = parcel.readString()
        auteur = parcel.readString()
        img = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(titre)
        parcel.writeString(desc)
        parcel.writeString(auteur)
        parcel.writeString(img)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Livre> {
        override fun createFromParcel(parcel: Parcel): Livre {
            return Livre(parcel)
        }

        override fun newArray(size: Int): Array<Livre?> {
            return arrayOfNulls(size)
        }
    }*/
}