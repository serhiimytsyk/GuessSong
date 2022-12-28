import React from 'react';
import ArtistSelector from './components/ArtistSelector.js';
import AlbumSelector from './components/AlbumSelector.js';
import Question from './components/Question.js';
import Score from './components/Score.js';
import './App.css';

const totalQuestions = 5;

class App extends React.Component {
  constructor(props) {
    super(props);
    this.setAlbums.bind(this);
    this.setQuestions.bind(this);
    this.correctAnswer.bind(this);
    this.wrongAnswer.bind(this);
    this.reloadGame.bind(this);
    this.restartGame.bind(this);

    this.state = {albums : null, questions : null, index : 0, score : 0};
  }

  setAlbums = (Albums) => {
    this.setState({albums : Albums});
  }

  setQuestions = (Questions) => {
    this.setState({questions : Questions});
  }

  correctAnswer = () => {
    this.setState({index : this.state.index + 1, score : this.state.score + 1});
  }

  wrongAnswer = () => {
    this.setState({index : this.state.index + 1});
  }

  reloadGame = () => {
    fetch('http://localhost:8080/api/lyrics/' + totalQuestions)
      .then((response) => {
        if (!response.ok) throw new Error(response.headers);
        else return response.json();
      })
      .then((list) => this.setState({questions : list, index : 0, score : 0}))
      .catch((error) => {console.log(error)});
  }

  restartGame = () => {
    this.setState({albums : null, currentAlbum : null, questions : null, index : 0, score : 0});
  }

  render() {
    var content;
    if (!this.state.albums) {
      content = <ArtistSelector albumSetter = {this.setAlbums}></ArtistSelector>;
    } else {
      if (!this.state.questions) {
        content = <AlbumSelector albums = {this.state.albums} questionSetter = {this.setQuestions} number = {totalQuestions}>

        </AlbumSelector>
      } else {
        if (this.state.index < totalQuestions) {
          content = <div key = {this.state.index}>
              <Question question = {this.state.questions[this.state.index]} 
                correntAnswerUpdater = {this.correctAnswer} wrongAnswerUpdater = {this.wrongAnswer}>

              </Question>
            </div>
        } else {
          content = <Score score = {this.state.score} maxScore = {totalQuestions} 
            reload = {this.reloadGame} restart = {this.restartGame}>

          </Score>
        }
      }
    }

    return (
      <div className="App">
        {content}
      </div>
    );
  }
  
}

export default App;
