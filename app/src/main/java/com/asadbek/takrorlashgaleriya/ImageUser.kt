package com.asadbek.takrorlashgaleriya

class ImageUser {
    var path:String? = null
    var image:ByteArray? = null
    constructor()
    constructor(path: String?, image: ByteArray?) {
        this.path = path
        this.image = image
    }

}