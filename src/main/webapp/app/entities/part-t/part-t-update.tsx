import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IModuleT } from 'app/shared/model/module-t.model';
import { getEntities as getModuleTs } from 'app/entities/module-t/module-t.reducer';
import { IModelT } from 'app/shared/model/model-t.model';
import { getEntities as getModelTs } from 'app/entities/model-t/model-t.reducer';
import { getEntity, updateEntity, createEntity, reset } from './part-t.reducer';
import { IPartT } from 'app/shared/model/part-t.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPartTUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPartTUpdateState {
  isNew: boolean;
  idsmoduleT: any[];
  modelTId: string;
}

export class PartTUpdate extends React.Component<IPartTUpdateProps, IPartTUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsmoduleT: [],
      modelTId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getModuleTs();
    this.props.getModelTs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { partTEntity } = this.props;
      const entity = {
        ...partTEntity,
        ...values,
        moduleTS: mapIdList(values.moduleTS)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/part-t');
  };

  render() {
    const { partTEntity, moduleTS, modelTS, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="monoHeartApp.partT.home.createOrEditLabel">Create or edit a PartT</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : partTEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="part-t-id">ID</Label>
                    <AvInput id="part-t-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="part-t-name">
                    Name
                  </Label>
                  <AvField id="part-t-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="part-t-moduleT">Module T</Label>
                  <AvInput
                    id="part-t-moduleT"
                    type="select"
                    multiple
                    className="form-control"
                    name="moduleTS"
                    value={partTEntity.moduleTS && partTEntity.moduleTS.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {moduleTS
                      ? moduleTS.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/part-t" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  moduleTS: storeState.moduleT.entities,
  modelTS: storeState.modelT.entities,
  partTEntity: storeState.partT.entity,
  loading: storeState.partT.loading,
  updating: storeState.partT.updating,
  updateSuccess: storeState.partT.updateSuccess
});

const mapDispatchToProps = {
  getModuleTs,
  getModelTs,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PartTUpdate);
