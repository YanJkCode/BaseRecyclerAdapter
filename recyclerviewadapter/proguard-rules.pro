-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,Annotation,EnclosingMethod,MethodParameters
-keep class *.R$ {*;}
# keep annotated by NotProguard
-keep @com.yanjkcode.recyclerviewadapter.base.NotProguard class * {*;}
-keep class * {
@com.yanjkcode.recyclerviewadapter.base.NotProguard <fields>;
}
-keepclassmembers class * {
@com.yanjkcode.recyclerviewadapter.base.NotProguard <methods>;
}
-keep interface * {
@com.yanjkcode.recyclerviewadapter.base.NotProguard <fields>;
}
-keepclassmembers interface * {
@com.yanjkcode.recyclerviewadapter.base.NotProguard <methods>;
}