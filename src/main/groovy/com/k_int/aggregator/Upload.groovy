package com.k_int.aggregator;

import groovy.swing.SwingBuilder
import groovy.beans.Bindable

class Upload {
   
   @Bindable int count = 0

   public static void main(String[] args) {
     Upload u = new Upload();
     u.run();
   }

   public Upload() {
   }

   public run() {
    new SwingBuilder().edt {
      frame(title: "Java Frame", size: [100, 100], locationRelativeTo: null, show: true) {
        gridLayout(cols: 1, rows: 2)
        label(text: bind(source: this, sourceProperty: "count", converter: { v ->  v? "Clicked $v times": ''}))
        button("Click me!", actionPerformed: { this.count++ })
      }
    }
  }
}
