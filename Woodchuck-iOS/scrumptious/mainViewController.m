//
//  mainViewController.m
//  Woodchuck
//
//  Created by Ali Nahm on 1/29/13.
//
//

#import "mainViewController.h"


#import "SCViewController.h"
#import "SCAppDelegate.h"
#import "SCLoginViewController.h"
#import "SCProtocols.h"
#import <AddressBook/AddressBook.h>
#import "TargetConditionals.h"

/* @interface SCViewController() < UITableViewDataSource,
FBFriendPickerDelegate,
UINavigationControllerDelegate,
FBPlacePickerDelegate,
CLLocationManagerDelegate,
UIActionSheetDelegate> */

@interface mainViewController ()

@property (strong, nonatomic) FBUserSettingsViewController *settingsViewController;


@end


@implementation mainViewController
@synthesize settingsViewController = _settingsViewController;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.title = @"Woodchuck";

    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]
                                              initWithTitle:@"Settings"
                                              style:UIBarButtonItemStyleBordered
                                              target:self
                                              action:@selector(settingsButtonWasPressed:)];

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)settingsButtonWasPressed:(id)sender {
    if (self.settingsViewController == nil) {
        self.settingsViewController = [[FBUserSettingsViewController alloc] init];
        self.settingsViewController.delegate = self;
    }
    [self.navigationController pushViewController:self.settingsViewController animated:YES];
}


@end
