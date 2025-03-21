//
//  AVAudioSession+Extensions.m
//  ChromeCast
//

#import "AVAudioSession+Extensions.h"
#import <MediaPlayer/MediaPlayer.h>
#import <objc/runtime.h>

@implementation AVAudioSession (Extensions)

+ (void)registerForVolumeNotifications:(id)observer selector:(SEL)selector {
    [[NSNotificationCenter defaultCenter] addObserver:observer
                                             selector:selector
                                                 name:@"AVSystemController_SystemVolumeDidChangeNotification"
                                               object:nil];
    
    // Need to create an audio session to receive volume change events
    NSError *error = nil;
    AVAudioSession *session = [AVAudioSession sharedInstance];
    [session setCategory:AVAudioSessionCategoryAmbient error:&error];
    if (error) {
        NSLog(@"Error setting up audio session: %@", error);
    }
    
    // Create a hidden MPVolumeView which is needed for volume button events
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    if (window) {
        MPVolumeView *volumeView = [[MPVolumeView alloc] initWithFrame:CGRectMake(-100, -100, 1, 1)];
        volumeView.hidden = YES;
        [window addSubview:volumeView];
        
        // Save a reference to the volume view to make sure it's not deallocated
        objc_setAssociatedObject(observer, "VolumeViewKey", volumeView, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
    }
}

+ (void)unregisterForVolumeNotifications:(id)observer {
    [[NSNotificationCenter defaultCenter] removeObserver:observer
                                                    name:@"AVSystemController_SystemVolumeDidChangeNotification"
                                                  object:nil];
    
    // Remove the volume view
    UIView *volumeView = objc_getAssociatedObject(observer, "VolumeViewKey");
    [volumeView removeFromSuperview];
    objc_setAssociatedObject(observer, "VolumeViewKey", nil, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

@end