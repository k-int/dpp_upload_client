package dppupload

import groovy.beans.Bindable

class DppuploadModel {
   @Bindable String baseDir = java.lang.System.getProperty('user.dir')
   @Bindable String dppUser
   @Bindable String dppPass
   @Bindable String dppAuthority
   @Bindable List resources = new ArrayList()
}
