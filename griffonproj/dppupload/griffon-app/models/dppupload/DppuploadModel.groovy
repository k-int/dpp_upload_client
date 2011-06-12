package dppupload

import groovy.beans.Bindable

class DppuploadModel {
   @Bindable String baseDir
   @Bindable String dppUser
   @Bindable String dppPass
   @Bindable String dppAuthority
   @Bindable List resources = new ArrayList()
}
