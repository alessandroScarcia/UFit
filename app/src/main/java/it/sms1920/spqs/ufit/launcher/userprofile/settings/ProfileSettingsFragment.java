package it.sms1920.spqs.ufit.launcher.userprofile.settings;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import it.sms1920.spqs.ufit.launcher.LauncherActivity;
import it.sms1920.spqs.ufit.launcher.R;
import it.sms1920.spqs.ufit.launcher.userprofile.login.LoginActivity;
import it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.email.EditEmailActivity;
import it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.password.EditPasswordActivity;
import it.sms1920.spqs.ufit.launcher.userprofile.settings.edit.personalinfo.EditPersonalInfoActivity;

/**
 * View that lets the user edit his profile information.
 */
public class ProfileSettingsFragment extends Fragment implements ProfileSettingsContract.View {
    private static final String TAG = ProfileSettingsFragment.class.getCanonicalName();

    private static final int RC_CHOOSE_IMAGE = 1567;
    private static final int RC_DELETE_PROFILE = 666;

    private ProfileSettingsContract.Presenter presenter;

    private ImageView imgProfile;

    public ProfileSettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup presenter reference
        presenter = new ProfileSettingsPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        final LauncherActivity launcher = (LauncherActivity) getActivity();
        if (launcher != null) {
            launcher.setToolbarNavigationButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launcher.insertProfileFragment();
                }
            });
        }

        // Setup views references
        imgProfile = view.findViewById(R.id.imgProfile);

        Button btnEditProfileImage = view.findViewById(R.id.btnEditProfileImage);
        Button btnEditPersonalInfo = view.findViewById(R.id.btnEditPersonalInfo);
        Button btnEditEmail = view.findViewById(R.id.btnEditEmail);
        Button btnEditPassword = view.findViewById(R.id.btnEditPassword);
        Button btnDeleteProfile = view.findViewById(R.id.btnDeleteProfile);

        Switch swEditRole = view.findViewById(R.id.swEditRole);

        btnEditProfileImage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onEditProfileImageClicked();
            }
        });

        btnEditPersonalInfo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onEditPersonalInfoClicked();
            }
        });

        btnEditEmail.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onEditEmailClicked();
            }
        });

        btnEditPassword.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onEditPasswordClicked();
            }
        });

        btnDeleteProfile.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDeleteProfileClicked();
            }
        });

        swEditRole.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.onEditRoleCheckedChanged(isChecked);
            }
        });

        return view;
    }

    @Override
    public void chooseImage() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), RC_CHOOSE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri;

        if (requestCode == RC_CHOOSE_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            presenter.onResultImageUri(imageUri);
        }
    }

    @Override
    public void startEditPersonalInfoActivity() {
        startActivity(new Intent(getContext(), EditPersonalInfoActivity.class));
    }

    @Override
    public void startEditEmailActivity() {
        startActivity(new Intent(getContext(), EditEmailActivity.class));
    }

    @Override
    public void startEditPasswordActivity() {
        startActivity(new Intent(getContext(), EditPasswordActivity.class));
    }

    @Override
    public void showConfirmDeleteProfileDialog() {
        DeleteProfileDialog deleteProfileDialog = new DeleteProfileDialog();
        deleteProfileDialog.setTargetFragment(this, RC_DELETE_PROFILE);
        deleteProfileDialog.show(getFragmentManager(), null);
    }

    @Override
    public void setProfileImage(String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.img_profile_placeholder)
                .into(imgProfile, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "picasso:success");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.w(TAG, "picasso:failure", e);
                    }
                });
    }

    @Override
    public void reauthenticate() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void insertChooseFragment() {
        LauncherActivity launcher = (LauncherActivity) getActivity();

        if (launcher == null) {
            throw new IllegalStateException(TAG + " Couldn't retrive launcher reference.");
        } else {
            launcher.insertChooseFragment();
        }
    }

    public void onDialogPositiveClick() {
        presenter.deleteProfile();
    }

    public static class DeleteProfileDialog extends DialogFragment {
        private final String TAG = DeleteProfileDialog.class.getCanonicalName();

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.profile_settings_delete_profile_msg)
                    .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.d(TAG, "positiveButtonClick");
                            ProfileSettingsFragment profileSettingsFragment = (ProfileSettingsFragment) getTargetFragment();
                            if (profileSettingsFragment != null) {
                                profileSettingsFragment.onDialogPositiveClick();
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.d(TAG, "negativeButtonClick");
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}