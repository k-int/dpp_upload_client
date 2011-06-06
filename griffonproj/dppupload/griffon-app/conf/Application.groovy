application {
    title = 'Dppupload'
    startupGroups = ['dppupload']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "dppupload"
    'dppupload' {
        model      = 'dppupload.DppuploadModel'
        view       = 'dppupload.DppuploadView'
        controller = 'dppupload.DppuploadController'
    }

}
