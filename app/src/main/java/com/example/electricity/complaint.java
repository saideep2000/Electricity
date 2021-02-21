package com.example.electricity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class complaint extends Fragment implements View.OnClickListener {

    FloatingActionButton report,fire,accident,pole,power,sound,wire,other;

    Animation rotatefrom;
    Animation rotateto;
    Animation rotateopen;
    Animation rotateclose;

    Boolean isOpen = false;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.complaint, container, false);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.wrapper, new recfragment(), "findThisFragment")
                .addToBackStack(null)
                .commit();

        report = (FloatingActionButton)view.findViewById(R.id.report);
        fire = (FloatingActionButton)view.findViewById(R.id.fire);
        accident = (FloatingActionButton)view.findViewById(R.id.accident);
        pole = (FloatingActionButton)view.findViewById(R.id.pole);
        power = (FloatingActionButton)view.findViewById(R.id.power);
        sound = (FloatingActionButton)view.findViewById(R.id.sound);
        wire = (FloatingActionButton)view.findViewById(R.id.wire);
        other = (FloatingActionButton)view.findViewById(R.id.other);

        rotateopen = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_open_anim);
        rotateclose = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_close_anim);
        rotatefrom = AnimationUtils.loadAnimation(getContext(),R.anim.from_button_anim);
        rotateto = AnimationUtils.loadAnimation(getContext(),R.anim.to_button_anim);

        report.setOnClickListener(this);
        fire.setOnClickListener(this);
        accident.setOnClickListener(this);
        pole.setOnClickListener(this);
        power.setOnClickListener(this);
        sound.setOnClickListener(this);
        wire.setOnClickListener(this);
        other.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.report:
                if(isOpen){
                    fire.startAnimation(rotatefrom);
                    accident.startAnimation(rotatefrom);
                    pole.startAnimation(rotatefrom);
                    power.startAnimation(rotatefrom);
                    sound.startAnimation(rotatefrom);
                    wire.startAnimation(rotatefrom);
                    other.startAnimation(rotatefrom);
                    report.startAnimation(rotateopen);

                    fire.setClickable(true);
                    accident.setClickable(true);
                    pole.setClickable(true);
                    power.setClickable(true);
                    sound.setClickable(true);
                    wire.setClickable(true);
                    other.setClickable(true);
                    isOpen = false;
                }
                else{
                    fire.startAnimation(rotateto);
                    accident.startAnimation(rotateto);
                    pole.startAnimation(rotateto);
                    power.startAnimation(rotateto);
                    sound.startAnimation(rotateto);
                    wire.startAnimation(rotateto);
                    other.startAnimation(rotateto);
                    report.startAnimation(rotateopen);

                    fire.setClickable(false);
                    accident.setClickable(false);
                    pole.setClickable(false);
                    power.setClickable(false);
                    sound.setClickable(false);
                    wire.setClickable(false);
                    other.setClickable(false);

                    isOpen = true;
//                    Toast.makeText(getActivity(), "Clicked Report", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.fire:
                Toast.makeText(getActivity(), "Fire Complaint's", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.wrapper, new recfragmentfire(), "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.accident:
                Toast.makeText(getActivity(), "Accident Complaint's", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.wrapper, new recfragmentaccident(), "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.pole:
                Toast.makeText(getActivity(), "Pole Complaint's", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.wrapper, new recfragmentpole(), "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.power:
                Toast.makeText(getActivity(), "Power Complaint's", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.wrapper, new recfragmentpower(), "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.sound:
                Toast.makeText(getActivity(), "Sound Complaint's", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.wrapper, new recfragmentsound(), "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.wire:
                Toast.makeText(getActivity(), "Wire Complaint's", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.wrapper, new recfragmentwire(), "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.other:
                Toast.makeText(getActivity(), "Other Complaint's", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.wrapper, new recfragmentother(), "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;



        }
    }
}