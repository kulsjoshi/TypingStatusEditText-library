# Get Typing Status of EditText


###### This library let you know if user is typing anything in EditText. It is the simpletest way to get typing status which is basically used in Chat application.


### Key features
- Simple implementation

# Usage

### Dependencies


- **Step 1**. Add the JitPack repository to your build file

  Add it in your root build.gradle at the end of repositories:

      allprojects {
	      repositories {
		      ...
		      maven { url 'https://jitpack.io' }
	      }
      }
    
- **Step 2**. Add the dependency

  Add it in your app module build.gradle:

      dependencies {
          ...
          implementation 'com.github.kulsjoshi:TypingStatusEditText-library:1.0'
      }
    
    
### Implementation

- **Step 1**. Add EditText in your xml file:

    ```
    <com.kuldeepjoshi.chatedittextlibrary.TypingStatusEditText
          android:id="@+id/etChatEditText"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_margin="8dp"
          android:padding="18dp"
          android:hint="Write something..."
          app:layout_constraintLeft_toLeftOf="parent"
          app:layout_constraintRight_toRightOf="parent"
          app:layout_constraintTop_toBottomOf="@id/txtInformation" />
        
- **Step 2**. Use custom listener in your Java class.

        etTypingStatusEditText.setOnTypingModified(new TypingStatusEditText.OnTypingModified() {
        
              @Override
              public void onIsTypingModified(EditText view, boolean isTyping) {

                  if (isTyping) {
                      Log.i(TAG, "onIsTypingModified: User started typing.");
                      txtTypingStatus.setText("Started Typing....");
                  } else {
                      Log.i(TAG, "onIsTypingModified: User stopped typing");
                      txtTypingStatus.setText("Stopped Typing....");
                  }

              }
        });
        
### Requirements
- minSdkVersion >= 20
- Androidx

# LICENSE!

# Let me know!
I’d be really happy if you send me a links to your projects where you use my component. Just send an email to kuls.droid@gmail.com And do let me know if you have any questions or suggestion regarding our work.
